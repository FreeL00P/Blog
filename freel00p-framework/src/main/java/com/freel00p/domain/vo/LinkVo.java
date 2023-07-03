package com.freel00p.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * LinkVo
 *
 * @author fj
 * @since 2023/7/2 22:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkVo {
    /**
     * 网站地址
     */
    private String address;
    private String description;
    private Long id;

    private String name;

    private String logo;
}
