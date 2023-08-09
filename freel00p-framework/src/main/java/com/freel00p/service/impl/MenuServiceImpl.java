package com.freel00p.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freel00p.constants.SystemConstants;
import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.entity.Menu;
import com.freel00p.domain.vo.MenuVo;
import com.freel00p.exception.SystemException;
import com.freel00p.service.MenuService;
import com.freel00p.mapper.MenuMapper;
import com.freel00p.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.freel00p.enums.AppHttpCodeEnum.MENU_HAS_CHILDREN;
import static com.freel00p.enums.AppHttpCodeEnum.PARENT_ID_NOT_MENU_ID;

/**
* @author freeloop
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service实现
* @createDate 2023-08-06 12:46:06
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService {

    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是管理员，返回所有的权限
        if(id == 1L){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType,SystemConstants.MENU, SystemConstants.BUTTON);
            wrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(wrapper);
            return menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
        }
        //否则返回所具有的权限
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        //判断是否是管理员
        if(SecurityUtils.isAdmin()){
            //如果是 获取所有符合要求的Menu
            menus = menuMapper.selectAllRouterMenu();
        }else{
            //否则  获取当前用户所具有的Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }

        //构建tree
        //先找出第一层的菜单  然后去找他们的子菜单设置到children属性中
        List<Menu> menuTree = builderMenuTree(menus,0L);
        return menuTree;
    }

    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(item->item.setChildren(getChildren(item,menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取存入参数的 子Menu集合
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m->m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());
        return childrenList;
    }

    @Override
    public ResponseResult queryList(String status, String menuName) {
        //构造查询条件
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotEmpty(menuName),Menu::getMenuName,menuName);
        wrapper.like(StrUtil.isNotEmpty(status),Menu::getStatus,status);
        wrapper.orderByAsc(Menu::getParentId);
        wrapper.orderByAsc(Menu::getOrderNum);
        List<Menu> list = this.list(wrapper);
        List<MenuVo> menuVos = BeanUtil.copyToList(list, MenuVo.class);
        return ResponseResult.okResult(menuVos);
    }

    @Override
    public ResponseResult saveMenu(Menu menu) {
        this.save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getMenuById(Long id) {
        Menu menu = this.getById(id);
        MenuVo menuVo = BeanUtil.copyProperties(menu, MenuVo.class);
        return ResponseResult.okResult(menuVo);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        //不能把自己的id当做父id
        if (menu.getId().equals(menu.getParentId())){
            throw new SystemException(PARENT_ID_NOT_MENU_ID);
        }
        this.updateById(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult removeMenu(Long id) {
        //查询当前菜单有无子菜单
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getParentId,id);
        List<Menu> list = this.list(wrapper);
        //如果有子菜单则不能删除
        if (CollUtil.isNotEmpty(list)){
            throw new SystemException(MENU_HAS_CHILDREN);
        }
        this.removeById(id);
        return ResponseResult.okResult();
    }
}




