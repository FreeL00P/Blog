package com.freel00p.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.freel00p.domain.entity.Role;

import java.util.List;

/**
* @author freeloop
* @description 针对表【sys_role(角色信息表)】的数据库操作Service
* @createDate 2023-08-06 12:46:06
*/
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);
}
