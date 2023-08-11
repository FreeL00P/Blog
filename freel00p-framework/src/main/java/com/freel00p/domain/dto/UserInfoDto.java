package com.freel00p.domain.dto;

import com.freel00p.domain.entity.Role;
import com.freel00p.domain.vo.UserVo;
import lombok.Data;

import java.util.List;

/**
 * UserInfoDto
 *
 * @author fj
 * @since 2023/8/11 21:57
 */
@Data
public class UserInfoDto {

    private List<Long> roleIds;

    private List<Role> roles;

    private UserVo user;
}

