package com.freel00p.domain.dto;

import lombok.Data;

/**
 * AddLinkDto
 *
 * @author fj
 * @since 2023/8/13 9:38
 */
@Data
public class AddLinkDto {
    private String address;
    private String description;
    private String logo;
    private String name;
    private String status;
}
