package cn.ultronxr.system.service.impl;

import cn.ultronxr.system.bean.mybatis.bean.Role;
import cn.ultronxr.system.bean.mybatis.bean.UserRole;
import cn.ultronxr.system.bean.mybatis.mapper.RoleMapper;
import cn.ultronxr.system.bean.mybatis.mapper.UserRoleMapper;
import cn.ultronxr.system.service.UserRoleService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    public boolean assignRoleForUser(Long roleId, Long userId) {
        UserRole userRoleKey = new UserRole();
        userRoleKey.setRoleId(roleId);
        userRoleKey.setUserId(userId);
        return userRoleMapper.insert(userRoleKey) > 0;
    }

    @Override
    public boolean removeRoleForUser(Long roleId, Long userId) {
        UserRole userRole = new UserRole();
        userRole.setRoleId(roleId);
        userRole.setUserId(userId);
        return userRoleMapper.deleteById(userRole) > 0;
    }

    @Override
    public List<Role> getRoleListForUser(Long userId) {
        List<UserRole> list = userRoleMapper.selectList(
                Wrappers.lambdaQuery(UserRole.class)
                        .eq(UserRole::getUserId, userId)
        );
        List<Long> roleIdList = list.stream().map(UserRole::getRoleId).collect(Collectors.toList());

        return roleMapper.selectList(
                Wrappers.lambdaQuery(Role.class)
                        .in(Role::getId, roleIdList)
                        .orderByAsc(Role::getId)
        );
    }
}
