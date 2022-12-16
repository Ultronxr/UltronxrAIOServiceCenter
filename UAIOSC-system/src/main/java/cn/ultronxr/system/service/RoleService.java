package cn.ultronxr.system.service;

import cn.ultronxr.system.bean.mybatis.bean.Role;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/13 14:03
 * @description 角色信息 service
 */
public interface RoleService {

    int createRole(Role role);

    int updateRole(Role role);

    int deleteRole(Long roleId);

    List<Role> queryRole(Role role);

}
