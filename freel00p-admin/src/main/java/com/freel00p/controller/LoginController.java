package com.freel00p.controller;

import cn.hutool.core.bean.BeanUtil;
import com.freel00p.domain.entity.LoginUser;
import com.freel00p.domain.entity.Menu;
import com.freel00p.domain.vo.AdminUserInfoVo;
import com.freel00p.domain.vo.RoutersVo;
import com.freel00p.domain.vo.UserInfoVo;
import com.freel00p.service.LoginService;
import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.entity.User;
import com.freel00p.enums.AppHttpCodeEnum;
import com.freel00p.exception.SystemException;
import com.freel00p.service.MenuService;
import com.freel00p.service.RoleService;
import com.freel00p.utils.SecurityUtils;
import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * LoginController
 *
 * @author fj
 * @since 2023/8/5 21:59
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }
    @GetMapping("getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());

        //获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanUtil.copyProperties(user, UserInfoVo.class);
        //封装数据返回

        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roleKeyList,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }
    @GetMapping("getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree的形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        //封装数据返回
        return ResponseResult.okResult(new RoutersVo(menus));
    }
    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }
}
