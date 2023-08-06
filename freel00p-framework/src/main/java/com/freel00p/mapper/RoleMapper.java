package com.freel00p.mapper;

import com.freel00p.domain.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author freeloop
* @description 针对表【sys_role(角色信息表)】的数据库操作Mapper
* @createDate 2023-08-06 12:46:06
* @Entity com.freel00p.domain/entity.SysRole
*/
public interface RoleMapper extends BaseMapper<Role> {


    @Select(" SELECT\n" +
            " r.`role_key`" +
            " FROM\n" +
            " `sys_user_role` ur" +
            " LEFT JOIN `sys_role` r ON ur.`role_id` = r.`id`" +
            " WHERE\n" +
            " ur.`user_id` = #{userId} AND" +
            " r.`status` = 0 AND" +
            " r.`del_flag` = 0")
    List<String> selectRoleKeyByUserId(Long userId);
}




