package com.freel00p.controller;

import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.dto.UserInfoDto;
import com.freel00p.domain.vo.UserInfoVo;
import com.freel00p.domain.vo.UserVo;
import com.freel00p.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.awt.windows.WPrinterJob;

/**
 * UserController
 *
 * @author fj
 * @since 2023/8/11 21:15
 */
@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询角色列表
     * @param pageNum
     * @param pageSize
     * @param userName
     * @param phonenumber
     * @param status
     * @return
     */
    @GetMapping("list")
    public ResponseResult queryList(Integer pageNum, Integer pageSize,String userName,String phonenumber,String status){
        return userService.queryList(pageNum,pageSize,userName,phonenumber,status);

    }

    /**
     * 添加用户
     * @param userVo
     * @return
     */
    @PostMapping
    public ResponseResult addUser(@RequestBody UserVo userVo){
        return userService.addUser(userVo);
    }

    @GetMapping("{id}")
    public ResponseResult getUserInfo(@PathVariable Long id){
        return userService.getUserInfo(id);
    }

    @PutMapping
    public ResponseResult updateUser(@RequestBody UserVo userVo){
        return userService.updateUser(userVo);
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }
}
