package com.freel00p.domain.vo;

import com.freel00p.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * RoutersVo
 *
 * @author fj
 * @since 2023/8/6 13:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutersVo {

    private List<Menu> menus;
}
