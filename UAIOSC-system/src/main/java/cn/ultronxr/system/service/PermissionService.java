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

}
