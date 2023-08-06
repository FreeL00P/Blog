package com.freel00p.service;

import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.dto.AddArticleDto;
import com.freel00p.domain.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author freeloop
* @description 针对表【fl_article(文章表)】的数据库操作Service
* @createDate 2023-07-01 21:34:06
*/
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto article);
}
