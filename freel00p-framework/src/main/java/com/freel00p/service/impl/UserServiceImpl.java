package com.freel00p.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.UserInfo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.dto.UserInfoDto;
import com.freel00p.domain.entity.Role;
import com.freel00p.domain.entity.User;
import com.freel00p.domain.entity.UserRole;
import com.freel00p.domain.vo.PageVo;
import com.freel00p.domain.vo.UserInfoVo;
import com.freel00p.domain.vo.UserVo;
import com.freel00p.enums.AppHttpCodeEnum;
import com.freel00p.exception.SystemException;
import com.freel00p.service.RoleService;
import com.freel00p.service.UserRoleService;
import com.freel00p.service.UserService;
import com.freel00p.mapper.UserMapper;
import com.freel00p.utils.SecurityUtils;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author freeloop
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2023-07-03 13:26:32
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;

    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户信息
        User user = userService.getById(userId);
        //封装成userInfoVo
        UserInfoVo userInfoVo = BeanUtil.copyProperties(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        //对数据进行非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //...
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    private boolean nickNameExist(String nickName) {
        User user = this.getOne(
                new LambdaQueryWrapper<User>().eq(User::getNickName, nickName)
        );
        return user != null;
    }

    private boolean userNameExist(String userName) {
        User user = this.getOne(
                new LambdaQueryWrapper<User>().eq(User::getUserName, userName)
        );
        return user != null;
    }

    @Override
    public ResponseResult queryList(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status) {
        //构造查询条件
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotEmpty(userName),User::getUserName,userName);
        wrapper.like(StrUtil.isNotEmpty(phonenumber),User::getPhonenumber,phonenumber);
        wrapper.eq(StrUtil.isNotEmpty(status), User::getStatus,status);
        //分页查询
        Page<User> userPage = new Page<>(pageNum,pageSize);
        Page<User> retPage = page(userPage, wrapper);
        PageVo pageVo = new PageVo();
        pageVo.setTotal(retPage.getTotal());
        pageVo.setRows(retPage.getRecords());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    @Transactional
    public ResponseResult addUser(UserVo userVo) {
        //保存用户信息
        User user = BeanUtil.copyProperties(userVo, User.class);
        this.save(user);
        //保存用户关联的角色信息
        List<Long> roleIds = userVo.getRoleIds();
        List<UserRole> userRoles=new ArrayList<>();
        for (Long roleId : roleIds) {
            UserRole userRole = new UserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(user.getId());
            userRoles.add(userRole);
        }
        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<UserInfoDto> getUserInfo(Long id) {
        //查询用户基本信息
        User user = this.getById(id);
        UserVo userVo = BeanUtil.copyProperties(user, UserVo.class);
        //查询用户拥有的角色id
        List<UserRole> userRoles = userRoleService.list(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, id));

        //提取出roleId
        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        userVo.setRoleIds(roleIds);
        //查询所有角色列表
        List<Role> roleList = roleService.listAllRole().getData();
        //封装数据
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setRoles(roleList);
        userInfoDto.setRoleIds(roleIds);
        userInfoDto.setUser(userVo);
        return ResponseResult.okResult(userInfoDto);
    }

    @Override
    public ResponseResult updateUser(UserVo userVo) {
        //删除用户当前角色信息
        userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userVo.getId()));

        //更新角色信息
        User user = BeanUtil.copyProperties(userVo, User.class);
        this.updateById(user);

        List<Long> roleIds = userVo.getRoleIds();
        List<UserRole> userRoles=new ArrayList<>();
        for (Long roleId : roleIds) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userVo.getId());
            userRole.setRoleId(roleId);
            userRoles.add(userRole);
        }
        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult deleteUser(Long id) {
        //删除用户当前角色信息
        userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, id));
        //删除角色信息
        this.removeById(id);
        return ResponseResult.okResult();
    }
}




