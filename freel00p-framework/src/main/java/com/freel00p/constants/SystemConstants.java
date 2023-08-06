package com.freel00p.constants;

import lombok.EqualsAndHashCode;

/**
 * SystemConstants
 *
 * @author fj
 * @since 2023/7/2 20:46
 */

public class SystemConstants
{
    /**
     *  文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     *  文章是正常分布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /**
     *  分类是草稿
     */
    public static final String  CATEGORY_STATUS_DRAFT = "1";
    /**
     *  分类是正常状态
     */
    public static final String  CATEGORY_STATUS_NORMAL = "0";
    /**
     *  友链是草稿
     */
    public static final int LINK_STATUS_DRAFT = 1;
    /**
     * 友链状态为审核通过
     */
    public static final int  LINK_STATUS_NORMAL = 0;
    /**
     * 评论类型为：文章评论
     */
    public static final String ARTICLE_COMMENT = "0";
    /**
     * 评论类型为：友联评论
     */
    public static final String LINK_COMMENT = "1";
    public static final String BUTTON = "C";
    public static final String MENU = "F";
    public static final String STATUS_NORMAL="0";
    /** 正常状态 */
    public static final String NORMAL = "0";
    public static final String ADMIN = "1";
}