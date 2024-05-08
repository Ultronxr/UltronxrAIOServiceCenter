package cn.ultronxr.system.service;

import cn.ultronxr.system.bean.mybatis.bean.Menu;
import cn.ultronxr.system.bean.mybatis.bean.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2024/05/05 01:52:16
 * @description 角色-菜单对应 service
 */
public interface RoleMenuService extends IService<RoleMenu> {

    /**
     * 根据角色 ID 列表获取对应的菜单列表
     * @param roleIdList
     * @return
     */
    List<Menu> getMenuListByRoleIdList(List<Long> roleIdList);

}
