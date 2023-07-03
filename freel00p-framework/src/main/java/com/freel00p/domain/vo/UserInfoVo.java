package com.freel00p.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * UserInfoVo
 *
 * @author fj
 * @since 2023/7/3 14:30
 */
@Data
@Accessors(chain = true)
public class UserInfoVo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    private String sex;

    private String email;


}
