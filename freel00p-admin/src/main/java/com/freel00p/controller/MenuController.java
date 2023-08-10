package com.freel00p.controller;

import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.entity.Menu;
import com.freel00p.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * MenuController
 *
 * @author fj
 * @since 2023/8/7 22:41
 */
@RestController
@RequestMapping("system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 菜单列表
     * @param status 菜单状态
     * @param menuName 菜单名称
     * @return 菜单列表
     */
    @GetMapping("/list")
    public ResponseResult list(String status ,String menuName ){
        return menuService.queryList(status,menuName);
    }

    /**
     * 新增菜单
     * @param menu
     * @return
     */
    @PostMapping
    public ResponseResult addMenu(@RequestBody Menu menu){
        return menuService.saveMenu(menu);
    }

    /**
     * 获取菜单信息
     * @param id 菜单id
     * @return
     */
    @GetMapping("{id}")
    public ResponseResult getMenu(@PathVariable Long id){
        return menuService.getMenuById(id);
    }

    @PutMapping
    public ResponseResult updateMenu(@RequestBody Menu menu){
        return menuService.updateMenu(menu);
    }

    @DeleteMapping("{id}")
    public ResponseResult removeMenu(@PathVariable Long id){
        return menuService.removeMenu(id);
    }

    /**
     * 查询菜单树
     * @return
     */
    @GetMapping("/treeselect")
    public ResponseResult getMenuTree(){
        return menuService.treeselect();
    }

    /**
     * 加载对应角色菜单列表树
     * @param id
     * @return
     */
    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult getRoleMenuTree(@PathVariable Long id){
        return menuService.getRoleMenuTree(id);
    }
}
