package com.freel00p.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.freel00p.admin.service.LoginService;
import com.freel00p.blog.config.RedisCache;
import com.freel00p.constants.RedisConstants;
import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.entity.LoginUser;
import com.freel00p.domain.entity.User;
import com.freel00p.domain.vo.BlogUserLoginVo;
import com.freel00p.domain.vo.UserInfoVo;
import com.freel00p.service.BlogLoginService;
import com.freel00p.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * LoginServiceImpl
 *
 * @author fj
 * @since 2023/8/5 22:02
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(token);
        //判断是否认证成功
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取用户id,生成token
        LoginUser loginUser =(LoginUser) authenticate.getPrincipal();
        Long userId = loginUser.getUser().getId();
        String jwt = JwtUtil.createJWT(userId.toString());
        //把用户信息保存到redis
        redisCache.setCacheObject(RedisConstants.REDIS_ADMIN_LOGIN_ID+userId, loginUser);
        //把token和UserInfo封装返回
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(jwt, BeanUtil.copyProperties(loginUser.getUser(), UserInfoVo.class));
        return ResponseResult.okResult(blogUserLoginVo);
    }

    @Override
    public ResponseResult logout() {
        //从security上下文中获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser  =(LoginUser) authentication.getPrincipal();
        //获取用户id
        Long userId = loginUser.getUser().getId();
        //删除redis中的用户信息
        redisCache.deleteObject(RedisConstants.REDIS_ADMIN_LOGIN_ID+userId);
        return ResponseResult.okResult();
    }
}
