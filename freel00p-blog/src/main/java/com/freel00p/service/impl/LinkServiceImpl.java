package com.freel00p.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freel00p.domain.entity.Link;
import com.freel00p.service.LinkService;
import com.freel00p.mapper.LinkMapper;
import org.springframework.stereotype.Service;

/**
* @author freeloop
* @description 针对表【fl_link(友链)】的数据库操作Service实现
* @createDate 2023-07-01 21:34:06
*/
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link>
    implements LinkService {

}




