package com.freel00p.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.freel00p.config.RedisCache;
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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Objects;

/**
 * BlogLoginServiceImpl
 *
 * @author fj
 * @since 2023/7/3 13:38
 */
@Service
public class BlogLoginServiceImpl implements BlogLoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
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
        redisCache.setCacheObject(RedisConstants.REDIS_BLOG_LOGIN_ID+userId, loginUser);
        //把token和UserInfo封装返回
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(jwt, BeanUtil.copyProperties(loginUser.getUser(), UserInfoVo.class));
        return ResponseResult.okResult(blogUserLoginVo);
    }
}
