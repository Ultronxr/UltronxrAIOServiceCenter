package cn.ultronxr.system.service.impl;

import cn.ultronxr.system.bean.mybatis.bean.Permission;
import cn.ultronxr.system.bean.mybatis.bean.RolePermission;
import cn.ultronxr.system.bean.mybatis.mapper.PermissionMapper;
import cn.ultronxr.system.bean.mybatis.mapper.RolePermissionMapper;
import cn.ultronxr.system.service.RolePermissionService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ultronxr
 * @date 2022/12/14 15:08
 * @description
 */
@Service
@Slf4j
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;


    @Override
    public boolean processPermissionForRole(Long roleId, List<Long> permissionIdList) {
        // 查出这个角色已经存在的权限 list
        List<RolePermission> theRoleAlreadyHasThesePerms = rolePermissionMapper.selectList(
                Wrappers.lambdaQuery(RolePermission.class)
                        .eq(RolePermission::getRoleId, roleId)
        );

        // 组装这个角色最终的权限 list
        List<RolePermission> targetPerms = new ArrayList<>(permissionIdList.size());
        permissionIdList.forEach(id -> targetPerms.add(new RolePermission(roleId, id)) );

        // 分别去除两个 list 中的重复权限
        List<RolePermission> temp = new ArrayList<>(theRoleAlreadyHasThesePerms);
        theRoleAlreadyHasThesePerms.removeAll(targetPerms);
        targetPerms.removeAll(temp);

        // 移除旧 list 的权限
        for(RolePermission rp : theRoleAlreadyHasThesePerms) {
            rolePermissionMapper.deleteById(rp);
        }
        // 赋予新 list 的权限
        for(RolePermission rp : targetPerms) {
            rolePermissionMapper.insert(rp);
        }

        return true;
    }

    @Override
    public List<Permission> getPermissionListForRole(Long roleId) {
        return getPermissionListForRoleList(List.of(roleId));
    }

    @Override
    public List<Permission> getPermissionListForRoleList(List<Long> roleIdList) {
        if(CollectionUtils.isEmpty(roleIdList)) {
            return Collections.emptyList();
        }

        List<RolePermission> list = rolePermissionMapper.selectList(
                Wrappers.lambdaQuery(RolePermission.class)
                        .in(RolePermission::getRoleId, roleIdList)
        );
        List<Long> permissionIdList = list.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());

        if(CollectionUtils.isEmpty(permissionIdList)) {
            return Collections.emptyList();
        }
        return permissionMapper.selectList(
                Wrappers.lambdaQuery(Permission.class)
                        .in(Permission::getId, permissionIdList)
                        .orderByAsc(Permission::getId)
        );
    }

    //@Override
    //public boolean assignPermissionForRole(Long roleId, List<Long> permissionIdList) {
    //    return false;
    //}
    //
    //@Override
    //public boolean removePermissionForRole(Long roleId, List<Long> permissionIdList) {
    //    return false;
    //}
}

