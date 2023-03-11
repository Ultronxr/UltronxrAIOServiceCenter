package cn.ultronxr.system.service;

import cn.ultronxr.system.bean.mybatis.bean.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/13 14:03
 * @description 角色信息 service
 */
public interface RoleService extends IService<Role> {

    List<Role> queryRole(Role role);

}
