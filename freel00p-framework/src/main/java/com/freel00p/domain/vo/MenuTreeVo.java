package com.freel00p.domain.vo;

import com.freel00p.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * MenuTreeVo
 *
 * @author fj
 * @since 2023/8/9 23:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuTreeVo {
    /**
     * 菜单ID
     */
    private Long id;

    /**
     * 菜单名称
     */
    private String label;

    /**
     * 父菜单ID
     */
    private Long parentId;

    private List<MenuTreeVo> children;
}
