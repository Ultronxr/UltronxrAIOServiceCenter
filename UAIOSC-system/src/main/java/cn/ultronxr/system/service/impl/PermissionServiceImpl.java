package cn.ultronxr.system.service.impl;

import cn.hutool.core.date.CalendarUtil;
import cn.hutool.db.sql.Condition;
import cn.hutool.db.sql.SqlUtil;
import cn.ultronxr.framework.cache.user.UserCache;
import cn.ultronxr.system.bean.mybatis.bean.Permission;
import cn.ultronxr.system.bean.mybatis.bean.PermissionExample;
import cn.ultronxr.system.bean.mybatis.bean.User;
import cn.ultronxr.system.bean.mybatis.mapper.PermissionMapper;
import cn.ultronxr.system.service.PermissionService;
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
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;


    @Override
    public int createPermission(Permission permission) {
        permission.setId(null);
        permission.setCreateBy(((User) UserCache.getUser()).getUsername());
        permission.setCreateTime(CalendarUtil.calendar().getTime());
        return permissionMapper.insert(permission);
    }

    @Override
    public int updatePermission(Permission permission) {
        permission.setUpdateBy(((User) UserCache.getUser()).getUsername());
        permission.setUpdateTime(CalendarUtil.calendar().getTime());
        return permissionMapper.updateByPrimaryKey(permission);
    }

    @Override
    public int deletePermission(Long permissionId) {
        return permissionMapper.deleteByPrimaryKey(permissionId);
    }

    @Override
    public List<Permission> queryPermission(Permission permission) {
        PermissionExample example = null;
        if(permission != null) {
            example = new PermissionExample();
            PermissionExample.Criteria criteria = example.createCriteria();

            if(StringUtils.isNotEmpty(permission.getPermissionName())) {
                criteria.andPermissionNameLike(SqlUtil.buildLikeValue(permission.getPermissionName(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(permission.getPermissionCode())) {
                criteria.andPermissionCodeLike(SqlUtil.buildLikeValue(permission.getPermissionCode(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(permission.getMenu())) {
                criteria.andMenuLike(SqlUtil.buildLikeValue(permission.getMenu(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(permission.getMenuType())) {
                criteria.andMenuTypeLike(SqlUtil.buildLikeValue(permission.getMenuType(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(permission.getRequest())) {
                criteria.andRequestLike(SqlUtil.buildLikeValue(permission.getRequest(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(permission.getNote())) {
                criteria.andNoteLike(SqlUtil.buildLikeValue(permission.getNote(), Condition.LikeType.Contains, false));
            }
        }
        return permissionMapper.selectByExample(example);
    }
}
