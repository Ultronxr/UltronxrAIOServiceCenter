package cn.ultronxr.system.service;

import cn.ultronxr.system.bean.mybatis.bean.Permission;
import cn.ultronxr.system.bean.mybatis.bean.RolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/14 15:07
 * @description 角色-权限 对应关系 service
 */
public interface RolePermissionService extends IService<RolePermission> {

    /**
     * 处理（赋予或移除）一个角色的权限。<br/>
     * 只需传入 角色ID 和 该角色最终拥有权限的ID列表 ，本方法会自动判断每个权限需要赋予还是移除。<br/>
     * 例：角色A事先已有这些权限：[1,2,3,5]<br/>
     *     传入的角色A最终的权限列表：[2,5,7,8]<br/>
     *     那么结果是：移除1,3权限，赋予7,8权限。
     * @param roleId           角色ID
     * @param permissionIdList 权限ID列表
     * @return
     */
    boolean processPermissionForRole(Long roleId, List<Long> permissionIdList);

    /**
     * 给一个角色赋予权限
     * @param roleId           角色ID
     * @param permissionIdList 权限ID列表
     * @return
     */
    //boolean assignPermissionForRole(Long roleId, List<Long> permissionIdList);

    /**
     * 移除一个角色的权限
     * @param roleId           角色ID
     * @param permissionIdList 权限ID列表
     * @return
     */
    //boolean removePermissionForRole(Long roleId, List<Long> permissionIdList);

    /**
     * 获取一个角色的所有权限
     * @param roleId 角色ID
     * @return {@code List<Permission>} 权限对象列表
     */
    List<Permission> getPermissionListForRole(Long roleId);

    /**
     * 获取多个角色的所有权限
     * @param roleIdList 角色 ID 列表
     * @return {@code List<Permission>} 权限对象列表
     */
    List<Permission> getPermissionListForRoleList(List<Long> roleIdList);

}
