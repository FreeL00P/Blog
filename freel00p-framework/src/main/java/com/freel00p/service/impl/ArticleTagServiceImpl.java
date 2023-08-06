package com.freel00p.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freel00p.domain.entity.ArticleTag;
import com.freel00p.service.ArticleTagService;
import com.freel00p.mapper.ArticleTagMapper;
import org.springframework.stereotype.Service;

/**
* @author freeloop
* @description 针对表【fl_article_tag(文章标签关联表)】的数据库操作Service实现
* @createDate 2023-08-06 21:21:54
*/
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag>
    implements ArticleTagService {

}




