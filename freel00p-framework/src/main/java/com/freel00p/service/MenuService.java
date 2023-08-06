package com.freel00p.service;

import com.freel00p.domain.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author freeloop
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service
* @createDate 2023-08-06 12:46:06
*/
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}
