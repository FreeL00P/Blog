package com.freel00p.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.entity.Article;
import com.freel00p.service.ArticleService;
import com.freel00p.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

import java.util.List;

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
        wrapper.eq(Article::getDelFlag, 0); //未删除
        wrapper.orderByDesc(Article::getViewCount);//按照浏览量倒序
        wrapper.eq(Article::getStatus, 0);//已发布
        //查询前10 条
        Page<Article> page = new Page<>(1, 10);
        page(page,wrapper);
        List<Article> records = page.getRecords();
        return ResponseResult.okResult(records);
    }
}




