package com.freel00p.mapper;

import com.freel00p.domain.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author freeloop
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Mapper
* @createDate 2023-08-06 12:46:06
* @Entity com.freel00p.domain/entity.SysMenu
*/
public interface MenuMapper extends BaseMapper<Menu> {

    @Select("SELECT" +
            " DISTINCT m.perms" +
            " FROM" +
            " `sys_user_role` ur" +
            " LEFT JOIN `sys_role_menu` rm ON ur.`role_id` = rm.`role_id`" +
            " LEFT JOIN `sys_menu` m ON m.`id` = rm.`menu_id`" +
            " WHERE" +
            " ur.`user_id` = #{userId} AND" +
            " m.`menu_type` IN ('C','F') AND" +
            " m.`status` = 0 AND" +
            " m.`del_flag` = 0")
    List<String> selectPermsByUserId(Long userId);

    @Select("SELECT " +
            "DISTINCT m.id, m.parent_id, m.menu_name, m.path, m.component, " +
            "m.visible, m.status, IFNULL(m.perms,'') AS perms, " +
            "m.is_frame,  m.menu_type, m.icon, m.order_num, " +
            "m.create_time " +
            "FROM " +
            "sys_menu m " +
            "WHERE " +
            "m.menu_type IN('C','M') AND " +
            "m.status=0 AND m.del_flag=0 " +
            "ORDER BY m.parent_id,m.order_num;" +
            "")
    List<Menu> selectAllRouterMenu();

    @Select(" SELECT" +
            " DISTINCT m.id, m.parent_id, m.menu_name, m.path, m.component, m.visible, " +
            " m.status, IFNULL(m.perms,'') AS perms, m.is_frame,  m.menu_type, m.icon, " +
            " m.order_num, m.create_time" +
            " FROM" +
            " `sys_user_role` ur" +
            " LEFT JOIN `sys_role_menu` rm ON ur.`role_id` = rm.`role_id`" +
            " LEFT JOIN `sys_menu` m ON m.`id` = rm.`menu_id`" +
            " WHERE" +
            " ur.`user_id` = #{userId} AND" +
            " m.`menu_type` IN ('C','M') AND" +
            " m.`status` = 0 AND" +
            " m.`del_flag` = 0" +
            " ORDER BY" +
            " m.parent_id,m.order_num")
    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}




