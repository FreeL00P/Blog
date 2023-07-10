package com.freel00p.controller;

import com.freel00p.domain.ResponseResult;
import com.freel00p.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CommentController
 *
 * @author fj
 * @since 2023/7/10 22:01
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 获取文章评论列表
     * @return
     */
    @GetMapping("/commentlist")
    public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize) {
        return commentService.commentList(articleId,pageNum,pageSize);
    }
}
