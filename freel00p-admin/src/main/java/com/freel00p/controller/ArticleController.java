package com.freel00p.controller;

import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.dto.AddArticleDto;
import com.freel00p.domain.dto.ArticleDto;
import com.freel00p.domain.vo.ArticleDetailVo;
import com.freel00p.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * ArticleController
 *
 * @author fj
 * @since 2023/8/6 21:14
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 写文章
     * @param article
     * @return
     */
    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto article){
        return articleService.add(article);
    }

    /**
     * 文章管理后台
     * @param pageNum
     * @param pageSize
     * @param articleDto
     * @return
     */
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, ArticleDto articleDto){
        return articleService.pageArticleList(pageNum,pageSize,articleDto);
    }

    /**
     * 更新文章的数据展示
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseResult getArticle(@PathVariable Long id){
        return articleService.getAddArticleDto(id);
    }

    /**
     * 更新文章
     * @param addArticleDto
     * @return
     */
    @PutMapping
    public ResponseResult updateArticle(@RequestBody AddArticleDto addArticleDto){
        return articleService.updateArticle(addArticleDto);
    }

    /**
     * 删除文章
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseResult deleteArticle(@PathVariable Long id){
        return articleService.removeArticle(id);
    }


}
