package com.freel00p.service;

import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.freel00p.domain.vo.HotArticleVo;

import java.util.List;

/**
* @author freeloop
* @description 针对表【fl_article(文章表)】的数据库操作Service
* @createDate 2023-07-01 21:34:06
*/
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();
}
