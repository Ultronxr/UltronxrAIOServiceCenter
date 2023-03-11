package cn.ultronxr.system.service.impl;

import cn.ultronxr.system.bean.mybatis.bean.Permission;
import cn.ultronxr.system.bean.mybatis.mapper.PermissionMapper;
import cn.ultronxr.system.service.PermissionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/13 14:03
 * @description
 */
@Service
@Slf4j
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;


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
}
