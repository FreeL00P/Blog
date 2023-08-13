package com.freel00p.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.dto.AddLinkDto;
import com.freel00p.domain.entity.Category;
import com.freel00p.domain.entity.Link;
import com.freel00p.domain.vo.*;
import com.freel00p.service.LinkService;
import com.freel00p.mapper.LinkMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.freel00p.constants.SystemConstants.LINK_STATUS_NORMAL;

/**
* @author freeloop
* @description 针对表【fl_link(友链)】的数据库操作Service实现
* @createDate 2023-07-01 21:34:06
*/
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link>
    implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        //查询出所有通过审核的友链
        LambdaQueryWrapper<Link> linkWrapper = new LambdaQueryWrapper<>();
        linkWrapper.eq(Link::getStatus, LINK_STATUS_NORMAL);
        List<Link> linkList = this.list(linkWrapper);
        //封装成VO
        List<LinkVo> linkVos = BeanUtil.copyToList(linkList, LinkVo.class);
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult queryList(Integer pageNum, Integer pageSize, String name, String status) {
        //构造查询条件
        LambdaQueryWrapper<Link> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotEmpty(status),Link::getStatus,status);
        wrapper.like(StrUtil.isNotEmpty(name),Link::getName,name);
        Page<Link> linkPage = new Page<>(pageNum,pageSize);
        Page<Link> retPage = page(linkPage, wrapper);
        List<PageLinkVo> linkListVos = BeanUtil.copyToList(retPage.getRecords(), PageLinkVo.class);

        PageVo pageVo = new PageVo();
        pageVo.setTotal(retPage.getTotal());
        pageVo.setRows(linkListVos);
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addLink(AddLinkDto addLinkDto) {
        Link link = BeanUtil.copyProperties(addLinkDto, Link.class);
        this.save(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getLink(Long id) {
        Link link = this.getById(id);
        LinkGetVo linkGetVo = BeanUtil.copyProperties(link, LinkGetVo.class);
        return ResponseResult.okResult(linkGetVo);
    }

    @Override
    public ResponseResult updateLink(LinkGetVo linkGetVo) {
        Link link = BeanUtil.copyProperties(linkGetVo, Link.class);
        this.updateById(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult removeLink(Long id) {
        this.removeById(id);
        return ResponseResult.okResult();
    }
}




