package cn.ultronxr.system.service;

import cn.ultronxr.system.bean.mybatis.bean.Role;
import cn.ultronxr.system.bean.mybatis.bean.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/14 15:07
 * @description 用户-角色 对应关系 service
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 给一个用户赋予一个角色
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return
     */
    boolean assignRoleForUser(Long userId, Long roleId);

    /**
     * 移除一个用户的一个角色
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return
     */
    boolean removeRoleForUser(Long userId, Long roleId);

    /**
     * 获取一个用户的所有角色
     * @param userId 用户ID
     * @return {@code List<Role>} 角色对象列表
     */
    List<Role> getRoleListForUser(Long userId);

}
