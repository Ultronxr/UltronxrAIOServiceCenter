package cn.ultronxr.system.service.impl;

import cn.hutool.core.date.CalendarUtil;
import cn.hutool.db.sql.Condition;
import cn.hutool.db.sql.SqlUtil;
import cn.ultronxr.framework.cache.user.UserCache;
import cn.ultronxr.system.bean.mybatis.bean.Role;
import cn.ultronxr.system.bean.mybatis.bean.RoleExample;
import cn.ultronxr.system.bean.mybatis.bean.User;
import cn.ultronxr.system.bean.mybatis.mapper.RoleMapper;
import cn.ultronxr.system.service.RoleService;
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
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public int createRole(Role role) {
        role.setId(null);
        role.setCreateBy(((User) UserCache.getUser()).getUsername());
        role.setCreateTime(CalendarUtil.calendar().getTime());
        return roleMapper.insertSelective(role);
    }

    @Override
    public int updateRole(Role role) {
        role.setUpdateBy(((User) UserCache.getUser()).getUsername());
        role.setUpdateTime(CalendarUtil.calendar().getTime());
        return roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public int deleteRole(Long roleId) {
        return roleMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public List<Role> queryRole(Role role) {
        RoleExample example = null;
        if(role != null) {
            example = new RoleExample();
            RoleExample.Criteria criteria = example.createCriteria();

            if(StringUtils.isNotEmpty(role.getName())) {
                criteria.andNameLike(SqlUtil.buildLikeValue(role.getName(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(role.getCode())) {
                criteria.andCodeLike(SqlUtil.buildLikeValue(role.getCode(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(role.getNote())) {
                criteria.andNoteLike(SqlUtil.buildLikeValue(role.getNote(), Condition.LikeType.Contains, false));
            }
        }
        return roleMapper.selectByExample(example);
    }
}
