package com.freel00p.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ArticleDto
 *
 * @author fj
 * @since 2023/8/7 21:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
    /**
     * 标题
     */
    private String title;


    /**
     * 文章摘要
     */
    private String summary;
}
