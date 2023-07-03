package com.freel00p.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freel00p.domain.entity.Tag;
import com.freel00p.service.TagService;
import com.freel00p.mapper.TagMapper;
import org.springframework.stereotype.Service;

/**
* @author freeloop
* @description 针对表【fl_tag(标签)】的数据库操作Service实现
* @createDate 2023-07-01 21:34:06
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService {

}




