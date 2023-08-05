package com.freel00p.admin.controller;

import com.freel00p.admin.service.LoginService;
import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.entity.User;
import com.freel00p.enums.AppHttpCodeEnum;
import com.freel00p.exception.SystemException;
import com.freel00p.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUsername())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }
}
