package cn.ultronxr.system.util;

import cn.ultronxr.system.bean.mybatis.bean.Menu;

import java.util.*;

/**
 * @author Ultronxr
 * @date 2024/05/05 19:44:21
 * @description 有关 菜单 的公共方法
 */
public class MenuUtils {

    /**
     * 递归组装菜单树
     *
     * @param parentId 父级菜单 ID
     * @param menuList 子菜单列表
     * @return
     */
    public static List<Menu> buildMenuTreeRecursively(String parentId, List<Menu> menuList) {
        List<Menu> tree = new ArrayList<>();
        menuList.forEach(menu -> {
            if(Objects.equals(parentId, menu.getParentId())) {
                menu.setChildren(buildMenuTreeRecursively(menu.getId(), menuList));
                tree.add(menu);
            }
        });
        return tree;
    }

}
