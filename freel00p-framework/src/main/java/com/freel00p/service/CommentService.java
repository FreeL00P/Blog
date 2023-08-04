package com.freel00p.service;

import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author freeloop
* @description 针对表【fl_comment(评论表)】的数据库操作Service
* @createDate 2023-07-01 21:34:06
*/
public interface CommentService extends IService<Comment> {

    ResponseResult addComment(Comment comment);

    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);
}
