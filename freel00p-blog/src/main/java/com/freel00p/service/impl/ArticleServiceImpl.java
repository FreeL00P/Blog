package com.freel00p.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.entity.Article;
import com.freel00p.domain.entity.Category;
import com.freel00p.domain.vo.ArticleDetailVo;
import com.freel00p.domain.vo.ArticleListVo;
import com.freel00p.domain.vo.HotArticleVo;
import com.freel00p.domain.vo.PageVo;
import com.freel00p.service.ArticleService;
import com.freel00p.mapper.ArticleMapper;
import com.freel00p.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        //查询文章分类名称
        Category category = categoryService.getById(article.getCategoryId());
        if (category!=null) article.setCategoryName(category.getName());
        ArticleDetailVo articleDetailVo = BeanUtil.copyProperties(article, ArticleDetailVo.class);
        return ResponseResult.okResult(articleDetailVo);
    }
}




