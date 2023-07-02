package com.freel00p.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.entity.Link;
import com.freel00p.domain.vo.LinkVo;
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
}




