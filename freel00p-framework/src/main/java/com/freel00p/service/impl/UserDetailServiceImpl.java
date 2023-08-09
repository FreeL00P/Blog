package com.freel00p.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.freel00p.constants.SystemConstants;
import com.freel00p.domain.entity.LoginUser;
import com.freel00p.domain.entity.User;
import com.freel00p.mapper.MenuMapper;
import com.freel00p.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Objects;

/**
 * UserDetailServiceImpl
 *
 * @author fj
 * @since 2023/7/3 14:00
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService{
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, username);
        User user = userMapper.selectOne(queryWrapper);
        //判断是否查到了用户信息
        if(Objects.isNull(user)) {
            //如果没有查到，抛出异常
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        //如果查到了，封装用户信息并返回
        //返回用户信息
        if(user.getType().equals(SystemConstants.ADMIN)){
            List<String> list = menuMapper.selectPermsByUserId(user.getId());
            return new LoginUser(user,list);
        }
        return new LoginUser(user,null);
    }
}
