package cn.ultronxr.system.service;

import cn.ultronxr.system.bean.mybatis.bean.Permission;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/13 14:02
 * @description 权限信息 service
 */
public interface PermissionService {

    int createPermission(Permission permission);

    int updatePermission(Permission permission);

    int deletePermission(Long permissionId);

    List<Permission> queryPermission(Permission permission);

}
