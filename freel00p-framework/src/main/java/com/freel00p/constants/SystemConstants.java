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

}