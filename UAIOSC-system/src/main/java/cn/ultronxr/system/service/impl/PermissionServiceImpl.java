package cn.ultronxr.system.service.impl;

import cn.ultronxr.framework.bean.JWSParseResult;
import cn.ultronxr.framework.jjwt.JWSTokenService;
import cn.ultronxr.system.bean.mybatis.bean.Permission;
import cn.ultronxr.system.bean.mybatis.bean.Role;
import cn.ultronxr.system.bean.mybatis.mapper.PermissionMapper;
import cn.ultronxr.system.service.PermissionService;
import cn.ultronxr.system.service.RolePermissionService;
import cn.ultronxr.system.service.UserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ultronxr
 * @date 2022/12/13 14:03
 * @description
 */
@Service
@Slf4j
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    private final PermissionMapper permissionMapper;

    private final UserRoleService userRoleService;

    private final RolePermissionService rolePermissionService;

    private final JWSTokenService jwsTokenService;

    public PermissionServiceImpl(
            PermissionMapper permissionMapper,
            UserRoleService userRoleService,
            RolePermissionService rolePermissionService,
            JWSTokenService jwsTokenService
    ) {
        this.permissionMapper = permissionMapper;
        this.userRoleService = userRoleService;
        this.rolePermissionService = rolePermissionService;
        this.jwsTokenService = jwsTokenService;
    }


    @Override
    public List<Permission> queryPermission(Permission permission) {
        LambdaQueryWrapper<Permission> wrapper = Wrappers.lambdaQuery();
        if(permission != null) {
            wrapper.like(StringUtils.isNotEmpty(permission.getPermissionName()), Permission::getPermissionName, permission.getPermissionName())
                    .like(StringUtils.isNotEmpty(permission.getPermissionCode()), Permission::getPermissionCode, permission.getPermissionCode())
                    .like(StringUtils.isNotEmpty(permission.getMenu()), Permission::getMenu, permission.getMenu())
                    .like(StringUtils.isNotEmpty(permission.getMenuType()), Permission::getMenuType, permission.getMenuType())
                    .like(StringUtils.isNotEmpty(permission.getRequest()), Permission::getRequest, permission.getRequest())
                    .like(StringUtils.isNotEmpty(permission.getNote()), Permission::getNote, permission.getNote());
        }
        return permissionMapper.selectList(wrapper);
    }

    @Override
    public List<String> getPermissionCodeList(String jwsToken) {
        JWSParseResult parseResult = jwsTokenService.parseToken(jwsToken);
        Long userId = parseResult.getUserId();
        return getPermissionCodeList(userId);
    }

    @Override
    public List<String> getPermissionCodeList(Long userId) {
        if(null == userId) {
            return null;
        }
        List<Long> roleIdList = userRoleService.getRoleListForUser(userId).stream().map(Role::getId).collect(Collectors.toList());
        List<Permission> permissionList = rolePermissionService.getPermissionListForRoleList(roleIdList);

        List<String> permissionCodeList = permissionList.stream().map(Permission::getPermissionCode).collect(Collectors.toList());
        return permissionCodeList;
    }

}
