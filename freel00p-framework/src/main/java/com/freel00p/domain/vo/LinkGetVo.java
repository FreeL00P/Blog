package com.freel00p.domain.vo;

import lombok.Data;

/**
 * LinkGetVo
 *
 * @author fj
 * @since 2023/8/13 9:45
 */
@Data
public class LinkGetVo {

    private Long id;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private String logo;

    /**
     *
     */
    private String description;

    /**
     * 网站地址
     */
    private String address;

    /**
     * 审核状态 (0代表审核通过，1代表审核未通过，2代表未审核)
     */
    private String status;
}
