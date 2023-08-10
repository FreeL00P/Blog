package com.freel00p.domain.dto;

import com.freel00p.domain.vo.MenuTreeVo;
import lombok.Data;
import org.omg.PortableInterceptor.LOCATION_FORWARD;

import java.util.List;

/**
 * RoleMenuVo
 *
 * @author fj
 * @since 2023/8/10 22:12
 */
@Data
public class RoleMenuVo {
  private List<MenuTreeVo> menus;
  private List<Long> checkedKeys;
}
