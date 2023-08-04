package com.freel00p.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.entity.Comment;
import com.freel00p.domain.entity.User;
import com.freel00p.domain.vo.CategoryVo;
import com.freel00p.domain.vo.CommentVo;
import com.freel00p.enums.AppHttpCodeEnum;
import com.freel00p.exception.SystemException;
import com.freel00p.service.CommentService;
import com.freel00p.mapper.CommentMapper;
import com.freel00p.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author freeloop
* @description 针对表【fl_comment(评论表)】的数据库操作Service实现
* @createDate 2023-07-01 21:34:06
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService {

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize) {
        //1.先查出文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,articleId);
        queryWrapper.eq(Comment::getType,0);//是文章评论
        queryWrapper.eq(Comment::getRootId,"-1");//根评论
        queryWrapper.orderByAsc(Comment::getCreateTime);
        //分页查询
        Page<Comment> commentPage = new Page<>(pageNum, pageSize);
        page(commentPage,queryWrapper);
        List<Comment> comments = commentPage.getRecords();
        //封装成Vo对象
        List<CommentVo> commentVos = this.toCommentVoList(comments);
        //2.查出根评论下的子评论
        for (CommentVo commentVo : commentVos) {
            commentVo.setChildren(this.getChildrenList(commentVo.getRootId()));
        }
        return ResponseResult.okResult(commentVos);
    }
    public List<CommentVo> getChildrenList(Long rootId){
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getType, 0);//是文章评论
        queryWrapper.eq(Comment::getRootId, rootId);//对应根评论id
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = this.list(queryWrapper);
        return this.toCommentVoList(comments);
    }
    private List<CommentVo> toCommentVoList(List<Comment> list){
        return list.stream().map(comment -> {
            CommentVo commentVo = BeanUtil.copyProperties(comment, CommentVo.class);
            //根据createBy查询用户信息
            Long createBy = commentVo.getCreateBy();
            User user = userService.getById(createBy);
            commentVo.setUsername(user.getNickName());
            //查询被回复的用户名
            if (commentVo.getToCommentUserId() != -1) {
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
            return commentVo;
        }).collect(Collectors.toList());
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        //判断评论内容
        if (!StrUtil.isEmpty(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        this.save(comment);
        return ResponseResult.okResult();
    }
}




