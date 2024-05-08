package cn.ultronxr.system.service.impl;

import cn.ultronxr.framework.bean.JWSParseResult;
import cn.ultronxr.framework.jjwt.JWSTokenService;
import cn.ultronxr.system.bean.mybatis.bean.Menu;
import cn.ultronxr.system.bean.mybatis.bean.UserRole;
import cn.ultronxr.system.bean.mybatis.mapper.MenuMapper;
import cn.ultronxr.system.service.MenuService;
import cn.ultronxr.system.service.RoleMenuService;
import cn.ultronxr.system.service.UserRoleService;
import cn.ultronxr.system.util.MenuUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author Ultronxr
 * @date 2024/05/05 01:42:15
 * @description
 */
@Service
@Slf4j
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private final JWSTokenService jwsTokenService;

    private final UserRoleService userRoleService;

    private final RoleMenuService roleMenuService;

    public MenuServiceImpl(
            JWSTokenService jwsTokenService,
            UserRoleService userRoleService,
            RoleMenuService roleMenuService
    ) {
        this.jwsTokenService = jwsTokenService;
        this.userRoleService = userRoleService;
        this.roleMenuService = roleMenuService;
    }


    @Override
    public List<Menu> getMenuList(String jwsToken) {
        JWSParseResult parseResult = jwsTokenService.parseToken(jwsToken);
        Long userId = parseResult.getUserId();
        if(null == userId) {
            return Collections.emptyList();
        }

        List<Long> roleIdList = userRoleService.listObjs(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId).select(UserRole::getRoleId), type -> (Long) type);
        List<Menu> menuList = roleMenuService.getMenuListByRoleIdList(roleIdList);
        List<Menu> resultList = MenuUtils.buildMenuTreeRecursively(null, menuList);

        return resultList;
    }

}
