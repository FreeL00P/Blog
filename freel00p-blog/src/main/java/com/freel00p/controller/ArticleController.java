package com.freel00p.controller;

import com.freel00p.domain.ResponseResult;
import com.freel00p.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * HelloController
 *
 * @author fj
 * @since 2023/7/1 21:35
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     *
     * @return  查询热门文章
     */
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){

        return articleService.hotArticleList();
    }

    /**
     * 分页查询 文章列表
     * @param pageNum 1
     * @param pageSize 10
     * @param categoryId 分类id
     * @return 文章列表
     */
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    /**
     * 获取文章信息
     * @param id 文章di
     * @return info
     */
    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }

    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }
}
