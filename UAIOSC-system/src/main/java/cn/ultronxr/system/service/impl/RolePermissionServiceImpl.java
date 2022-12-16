package cn.ultronxr.system.service.impl;

import cn.ultronxr.system.bean.mybatis.bean.Permission;
import cn.ultronxr.system.bean.mybatis.bean.PermissionExample;
import cn.ultronxr.system.bean.mybatis.bean.RolePermissionExample;
import cn.ultronxr.system.bean.mybatis.bean.RolePermissionKey;
import cn.ultronxr.system.bean.mybatis.mapper.PermissionMapper;
import cn.ultronxr.system.bean.mybatis.mapper.RolePermissionMapper;
import cn.ultronxr.system.service.RolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ultronxr
 * @date 2022/12/14 15:08
 * @description
 */
@Service
@Slf4j
public class RolePermissionServiceImpl implements RolePermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;


    @Override
    public boolean processPermissionForRole(Long roleId, List<Long> permissionIdList) {
        // 查出这个角色已经存在的权限 list
        RolePermissionExample example = new RolePermissionExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        List<RolePermissionKey> theRoleAlreadyHasThesePerms = rolePermissionMapper.selectByExample(example);

        // 组装这个角色最终的权限 list
        List<RolePermissionKey> targetPerms = new ArrayList<>(permissionIdList.size());
        permissionIdList.forEach(id -> targetPerms.add(new RolePermissionKey(roleId, id)) );

        // 分别去除两个 list 中的重复权限
        List<RolePermissionKey> temp = new ArrayList<>(theRoleAlreadyHasThesePerms);
        theRoleAlreadyHasThesePerms.removeAll(targetPerms);
        targetPerms.removeAll(temp);

        // 移除旧 list 的权限
        for(RolePermissionKey rp : theRoleAlreadyHasThesePerms) {
            rolePermissionMapper.deleteByPrimaryKey(rp);
        }
        // 赋予新 list 的权限
        for(RolePermissionKey rp : targetPerms) {
            rolePermissionMapper.insert(rp);
        }

        return true;
    }

    @Override
    public List<Permission> getPermissionListForRole(Long roleId) {
        RolePermissionExample example = new RolePermissionExample();
        RolePermissionExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        List<RolePermissionKey> list = rolePermissionMapper.selectByExample(example);
        List<Long> permissionIdList = list.stream().map(RolePermissionKey::getPermissionId).collect(Collectors.toList());

        PermissionExample permExample = new PermissionExample();
        PermissionExample.Criteria permCriteria = permExample.createCriteria();
        permCriteria.andIdIn(permissionIdList);
        permExample.setOrderByClause("id asc");
        return permissionMapper.selectByExample(permExample);
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

