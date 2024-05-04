package cn.ultronxr.system.service;

import cn.ultronxr.system.bean.mybatis.bean.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/13 14:02
 * @description 权限信息 service
 */
public interface PermissionService extends IService<Permission> {

    List<Permission> queryPermission(Permission permission);

    /**
     * 根据登录 token 获取权限代码列表
     *
     * @param jwsToken 登录 token
     * @return 权限代码列表
     */
    List<String> getPermissionCodeList(String jwsToken);

    /**
     * 根据用户 ID 获取权限代码列表
     *
     * @param userId 用户 ID
     * @return 权限代码列表
     */
    List<String> getPermissionCodeList(Long userId);

}
