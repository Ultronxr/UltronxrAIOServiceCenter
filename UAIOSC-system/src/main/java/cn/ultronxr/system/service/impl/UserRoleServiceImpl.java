package cn.ultronxr.system.service.impl;

import cn.ultronxr.system.bean.mybatis.bean.Role;
import cn.ultronxr.system.bean.mybatis.bean.RoleExample;
import cn.ultronxr.system.bean.mybatis.bean.UserRoleExample;
import cn.ultronxr.system.bean.mybatis.bean.UserRoleKey;
import cn.ultronxr.system.bean.mybatis.mapper.RoleMapper;
import cn.ultronxr.system.bean.mybatis.mapper.UserRoleMapper;
import cn.ultronxr.system.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ultronxr
 * @date 2022/12/14 15:09
 * @description
 */
@Service
@Slf4j
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    public boolean assignRoleForUser(Long roleId, Long userId) {
        UserRoleKey userRoleKey = new UserRoleKey();
        userRoleKey.setRoleId(roleId);
        userRoleKey.setUserId(userId);
        return userRoleMapper.insert(userRoleKey) > 0;
    }

    @Override
    public boolean removeRoleForUser(Long roleId, Long userId) {
        UserRoleKey userRoleKey = new UserRoleKey();
        userRoleKey.setRoleId(roleId);
        userRoleKey.setUserId(userId);
        return userRoleMapper.deleteByPrimaryKey(userRoleKey) > 0;
    }

    @Override
    public List<Role> getRoleListForUser(Long userId) {
        UserRoleExample example = new UserRoleExample();
        UserRoleExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<UserRoleKey> list = userRoleMapper.selectByExample(example);
        List<Long> roleIdList = list.stream().map(UserRoleKey::getRoleId).collect(Collectors.toList());

        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria roleCriteria = roleExample.createCriteria();
        roleCriteria.andIdIn(roleIdList);
        roleExample.setOrderByClause("id asc");
        return roleMapper.selectByExample(roleExample);
    }
}
