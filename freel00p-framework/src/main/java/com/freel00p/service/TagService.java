package com.freel00p.service;

import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.dto.TagListDto;
import com.freel00p.domain.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.freel00p.domain.vo.PageVo;
import com.freel00p.domain.vo.TagVo;

import java.util.List;

/**
* @author freeloop
* @description 针对表【fl_tag(标签)】的数据库操作Service
* @createDate 2023-07-01 21:34:06
*/
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    void saveTag(TagListDto tag);

    List<TagVo> listAllTag();
}
