package com.freel00p.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freel00p.domain.entity.User;
import com.freel00p.service.UserService;
import com.freel00p.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author freeloop
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2023-07-03 13:26:32
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}




