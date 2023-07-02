package com.freel00p.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * ArticleDetailVo
 *
 * @author fj
 * @since 2023/7/2 22:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailVo {


    /**
     * 所属分类id
     */
    private Long categoryId;

    /**
     * 分类名
     */
    private String categoryName;

    /**
     * 文章内容
     */
    private String content;

    private Date createTime;

    private Long id;
    /**
     * 是否允许评论 1是，0否
     */
    private String isComment;
    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 标题
     */
    private String title;
    /**
     * 访问量
     */
    private Long viewCount;

}
