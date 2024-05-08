package cn.ultronxr.system.service.impl;

import cn.ultronxr.system.bean.mybatis.bean.Menu;
import cn.ultronxr.system.bean.mybatis.bean.RoleMenu;
import cn.ultronxr.system.bean.mybatis.mapper.MenuMapper;
import cn.ultronxr.system.bean.mybatis.mapper.MenuMetaMapper;
import cn.ultronxr.system.bean.mybatis.mapper.RoleMenuMapper;
import cn.ultronxr.system.service.RoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author Ultronxr
 * @date 2024/05/05 01:52:45
 * @description
 */
@Service
@Slf4j
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    private final MenuMapper menuMapper;

    private final MenuMetaMapper menuMetaMapper;

    public RoleMenuServiceImpl(MenuMapper menuMapper, MenuMetaMapper menuMetaMapper) {
        this.menuMapper = menuMapper;
        this.menuMetaMapper = menuMetaMapper;
    }


    @Override
    public List<Menu> getMenuListByRoleIdList(List<Long> roleIdList) {
        if(null == roleIdList || roleIdList.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> menuIdList = this.listObjs(new LambdaQueryWrapper<RoleMenu>().in(RoleMenu::getRoleId, roleIdList).select(RoleMenu::getMenuId), type -> (String) type);
        List<Menu> menuList = menuMapper.selectList(new LambdaQueryWrapper<Menu>().in(Menu::getId, menuIdList).orderByAsc(Menu::getId));
        menuList.forEach(menu -> {
            menu.setMeta(menuMetaMapper.selectById(menu.getId()));
        });
        return menuList;
    }

}
