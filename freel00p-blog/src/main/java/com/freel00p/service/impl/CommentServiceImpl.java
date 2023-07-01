package com.freel00p.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freel00p.domain.entity.Comment;
import com.freel00p.service.CommentService;
import com.freel00p.mapper.CommentMapper;
import org.springframework.stereotype.Service;

/**
* @author freeloop
* @description 针对表【fl_comment(评论表)】的数据库操作Service实现
* @createDate 2023-07-01 21:34:06
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService {

}




