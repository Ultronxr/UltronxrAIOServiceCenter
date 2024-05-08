package cn.ultronxr.system.service;

import cn.ultronxr.system.bean.mybatis.bean.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2024/05/05 01:41:40
 * @description 菜单 service
 */
public interface MenuService extends IService<Menu> {

    /**
     * 根据当前用户，获取对应的菜单列表
     *
     * @param jwsToken 当前登录用户 JWS token
     * @return 对应的菜单列表
     */
    List<Menu> getMenuList(String jwsToken);

}
