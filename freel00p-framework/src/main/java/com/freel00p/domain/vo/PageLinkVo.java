package com.freel00p.domain.vo;

import lombok.Data;

/**
 * PageLinkVo
 *
 * @author fj
 * @since 2023/8/13 9:36
 */
@Data
public class PageLinkVo {

    /**
     * 网站地址
     */
    private String address;
    private String description;
    private Long id;

    private String name;
    private String status;
}
