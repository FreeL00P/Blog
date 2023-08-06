package com.freel00p.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TagListDto
 *
 * @author fj
 * @since 2023/8/6 20:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagListDto {
    /**
     * 标签名
     */
    private String name;

    /**
     * 备注
     */
    private String remark;
}
