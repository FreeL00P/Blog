package com.freel00p.controller;

import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.dto.AddRoleDto;
import com.freel00p.domain.dto.RoleStatusDto;
import com.freel00p.domain.entity.Role;
import com.freel00p.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * RoleController
 *
 * @author fj
 * @since 2023/8/9 22:42
 */
@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     *
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param roleName 角色名
     * @param status 状态
     * @return 角色列表
     */
    @GetMapping("/list")
    public ResponseResult queryList(Integer pageNum, Integer pageSize,String roleName,String status){
        return roleService.queryList(pageNum,pageSize,roleName,status);
    }

    /**
     * 修改角色状态
     * @return ret
     */
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody RoleStatusDto roleStatusDto){
        return roleService.changeStatus(roleStatusDto);
    }

    @PostMapping
    public ResponseResult addRole(@RequestBody AddRoleDto addRoleDto){
        return roleService.saveRole(addRoleDto);
    }

}
