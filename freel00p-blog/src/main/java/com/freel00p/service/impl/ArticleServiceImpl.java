package com.freel00p.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.entity.Article;
import com.freel00p.domain.vo.HotArticleVo;
import com.freel00p.service.ArticleService;
import com.freel00p.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

import java.util.List;
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
}




