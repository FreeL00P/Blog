package com.freel00p.service;

import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.dto.AddLinkDto;
import com.freel00p.domain.entity.Link;
import com.baomidou.mybatisplus.extension.service.IService;
import com.freel00p.domain.vo.LinkGetVo;

/**
* @author freeloop
* @description 针对表【fl_link(友链)】的数据库操作Service
* @createDate 2023-07-01 21:34:06
*/
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult queryList(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addLink(AddLinkDto addLinkDto);

    ResponseResult getLink(Long id);

    ResponseResult updateLink(LinkGetVo linkGetVo);

    ResponseResult removeLink(Long id);
}
