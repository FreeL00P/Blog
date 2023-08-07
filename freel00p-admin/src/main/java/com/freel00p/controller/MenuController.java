package com.freel00p.controller;

import com.freel00p.domain.ResponseResult;
import com.freel00p.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/list")
    public ResponseResult list(String status ,String menuName ){
        return menuService.queryList(status,menuName);
    }
}
