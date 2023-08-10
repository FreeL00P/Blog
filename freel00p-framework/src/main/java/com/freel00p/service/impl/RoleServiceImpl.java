package com.freel00p.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.dto.AddRoleDto;
import com.freel00p.domain.dto.RoleStatusDto;
import com.freel00p.domain.entity.Menu;
import com.freel00p.domain.entity.Role;
import com.freel00p.domain.entity.RoleMenu;
import com.freel00p.domain.vo.PageVo;
import com.freel00p.mapper.MenuMapper;
import com.freel00p.service.MenuService;
import com.freel00p.service.RoleMenuService;
import com.freel00p.service.RoleService;
import com.freel00p.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
* @author freeloop
* @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
* @createDate 2023-08-06 12:46:06
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员 如果是返回集合中只需要有admin
        if(id == 1L){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult queryList(Integer pageNum, Integer pageSize, String roleName, String status) {
        //构造查询条件
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotEmpty(roleName),Role::getRoleName,roleName);
        wrapper.eq(StrUtil.isNotEmpty(status),Role::getStatus,status);
        //分页查询
        Page<Role> rolePage = new Page<>(pageNum,pageSize);
        Page<Role> ret = page(rolePage, wrapper);
        PageVo pageVo = new PageVo();
        pageVo.setRows(ret.getRecords());
        pageVo.setTotal(ret.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult changeStatus(RoleStatusDto roleStatusDto) {
        Role role = new Role();
        role.setStatus(roleStatusDto.getStatus());
        role.setId(roleStatusDto.getRoleId());
        this.updateById(role);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult saveRole(AddRoleDto addRoleDto) {
        //保存角色信息
        Role role = BeanUtil.copyProperties(addRoleDto, Role.class);
        this.save(role);
        //保存角色对应的菜单
        List<Long> menuIds = addRoleDto.getMenuIds();
        ArrayList<RoleMenu> roleMenus = new ArrayList<>();
        for (Long menuId : menuIds) {
            roleMenus.add( new RoleMenu(role.getId(),menuId));
        }
        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRoleInfo(Long id) {
        Role role = this.getById(id);
        return ResponseResult.okResult(role);
    }

    @Override
    @Transactional
    public ResponseResult updateRole(AddRoleDto addRoleDto) {
        //删除当前拥有的菜单权限
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId,addRoleDto.getId());
        roleMenuService.remove(wrapper);
        //更新角色信息
        Role role = BeanUtil.copyProperties(addRoleDto, Role.class);
        this.updateById(role);
        //更新角色对应的菜单
        List<Long> menuIds = addRoleDto.getMenuIds();
        ArrayList<RoleMenu> roleMenus = new ArrayList<>();
        for (Long menuId : menuIds) {
            roleMenus.add( new RoleMenu(role.getId(),menuId));
        }
        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteRole(Long id) {
        //删除角色的菜单权限
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId,id);
        roleMenuService.remove(wrapper);
        this.removeById(id);
        return ResponseResult.okResult();
    }
}




