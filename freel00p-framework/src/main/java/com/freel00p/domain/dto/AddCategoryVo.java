package com.freel00p.domain.dto;

import lombok.Data;

/**
 * AddCategoryVo
 *
 * @author fj
 * @since 2023/8/13 9:23
 */
@Data
public class AddCategoryVo {


    /**
     * 分类名
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    private String status;
}
