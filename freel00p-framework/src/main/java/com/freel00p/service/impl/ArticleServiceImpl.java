package com.freel00p.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.intern.InternUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.util.IntUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freel00p.config.RedisCache;
import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.dto.AddArticleDto;
import com.freel00p.domain.dto.ArticleDto;
import com.freel00p.domain.entity.Article;
import com.freel00p.domain.entity.ArticleTag;
import com.freel00p.domain.entity.Category;
import com.freel00p.domain.vo.ArticleDetailVo;
import com.freel00p.domain.vo.ArticleListVo;
import com.freel00p.domain.vo.HotArticleVo;
import com.freel00p.domain.vo.PageVo;
import com.freel00p.service.ArticleService;
import com.freel00p.mapper.ArticleMapper;
import com.freel00p.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.freel00p.constants.RedisConstants.ARTICLE_VIEW_COUNT;
import static com.freel00p.constants.SystemConstants.ARTICLE_STATUS_NORMAL;

/**
* @author freeloop
* @description 针对表【fl_article(文章表)】的数据库操作Service实现
* @createDate 2023-07-01 21:34:06
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleTagServiceImpl articleTagService;


    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        //wrapper.eq(Article::getDelFlag, 0); //未删除
        wrapper.orderByDesc(Article::getViewCount);//按照浏览量倒序
        wrapper.eq(Article::getStatus, ARTICLE_STATUS_NORMAL);//已发布
        //查询前10 条
        Page<Article> page = new Page<>(1, 10);
        page(page,wrapper);
        List<Article> records = page.getRecords();
        List<HotArticleVo> hotArticleVos = records.stream().map(article -> {
            HotArticleVo hotArticleVo = new HotArticleVo();
            BeanUtil.copyProperties(article,hotArticleVo);
            return hotArticleVo;
        }).collect(Collectors.toList());


        return ResponseResult.okResult(hotArticleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //构建查询条件
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0 ,Article::getCategoryId,categoryId);//获取对应分类的文章
        articleWrapper.eq(Article::getStatus, ARTICLE_STATUS_NORMAL);//已发布的文章
        articleWrapper.orderByDesc(Article::getIsTop);
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page,articleWrapper);

        //查询categoryName
        List<Article> articles = page.getRecords().stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
        //封装查询结果
        List<ArticleListVo> articleListVos = BeanUtil.copyToList(articles, ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章信息
        Article article = this.getById(id);
        //查询文章浏览信息
        Integer viewCount = redisCache.getCacheMapValue(ARTICLE_VIEW_COUNT, id.toString());
        if (!Objects.isNull(viewCount))  article.setViewCount(viewCount.longValue());
        //查询文章分类名称
        Category category = categoryService.getById(article.getCategoryId());
        if (category!=null) article.setCategoryName(category.getName());
        ArticleDetailVo articleDetailVo = BeanUtil.copyProperties(article, ArticleDetailVo.class);
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中对应 id的浏览量
        redisCache.incrementCacheMapValue(ARTICLE_VIEW_COUNT,id.toString(),1);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult add(AddArticleDto articleDto) {
        //添加 博客
        Article article = BeanUtil.copyProperties(articleDto, Article.class);
        save(article);


        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        //添加 博客和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult pageArticleList(Integer pageNum, Integer pageSize, ArticleDto articleDto) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotEmpty(articleDto.getTitle()),Article::getTitle,articleDto.getTitle());
        wrapper.like(StrUtil.isNotEmpty(articleDto.getSummary()), Article::getSummary, articleDto.getSummary());
        Page<Article> articlePage = new Page<>();
        articlePage.setSize(pageSize);
        articlePage.setCurrent(pageNum);
        Page<Article> page = page(articlePage, wrapper);
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult updateArticle(AddArticleDto addArticleDto) {
        //删除文章关联tag表的信息
        List<Long> tagIds = addArticleDto.getTags();
        LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleTag::getArticleId,addArticleDto.getId());
        articleTagService.remove(wrapper);
        //更新文章关联的tag
        List<ArticleTag> articleTags = new ArrayList<>();
        for (Long tagId : tagIds) {
            ArticleTag articleTag = new ArticleTag();
            articleTag.setArticleId(addArticleDto.getId());
            articleTag.setTagId(tagId);
            articleTags.add(articleTag);
        }
        articleTagService.saveBatch(articleTags);
        //查询文章分类名称
        Category category = categoryService.getById(addArticleDto.getCategoryId());
        //更新文章数据
        Article article = BeanUtil.copyProperties(addArticleDto, Article.class);
        article.setCategoryName(category.getName());
        this.updateById(article);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult removeArticle(Long id) {
        //删除文章关联tag表的信息
        LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleTag::getArticleId,id);
        articleTagService.remove(wrapper);
        //删除文章信息
        this.removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getAddArticleDto(Long id) {
        AddArticleDto addArticleDto = new AddArticleDto();
        //查询文章信息
        Article article = this.getById(id);
        //查询标签信息
        List<ArticleTag> tags = articleTagService.list(new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getArticleId, id));
        List<Long> tagIds = tags.stream().map(ArticleTag::getTagId).collect(Collectors.toList());
        //封装数据
        addArticleDto.setTags(tagIds);
        BeanUtil.copyProperties(article,addArticleDto);
        return ResponseResult.okResult(addArticleDto);
    }
}




