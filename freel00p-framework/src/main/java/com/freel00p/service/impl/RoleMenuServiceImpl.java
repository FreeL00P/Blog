package com.freel00p.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freel00p.domain.entity.RoleMenu;
import com.freel00p.service.RoleMenuService;
import com.freel00p.mapper.SysRoleMenuMapper;
import org.springframework.stereotype.Service;

/**
* @author freeloop
* @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Service实现
* @createDate 2023-08-09 23:29:16
*/
@Service
public class RoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, RoleMenu>
    implements RoleMenuService {

}




