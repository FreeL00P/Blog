package com.freel00p.service;

import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.freel00p.domain.vo.UserInfoVo;
import com.freel00p.domain.vo.UserVo;

/**
* @author freeloop
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2023-07-03 13:26:32
*/
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult queryList(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status);

    ResponseResult addUser(UserVo userVo);

    ResponseResult getUserInfo(Long id);

    ResponseResult updateUser(UserVo userInfoVo);

    ResponseResult deleteUser(Long id);
}
