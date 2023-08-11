package com.freel00p.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freel00p.domain.entity.UserRole;
import com.freel00p.service.UserRoleService;
import com.freel00p.mapper.SysUserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author freeloop
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service实现
* @createDate 2023-08-11 21:46:23
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, UserRole>
    implements UserRoleService {

}




