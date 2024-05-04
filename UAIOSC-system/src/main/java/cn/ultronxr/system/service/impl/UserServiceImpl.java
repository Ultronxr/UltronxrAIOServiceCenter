package cn.ultronxr.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.ultronxr.framework.bean.JWSParseResult;
import cn.ultronxr.framework.jjwt.JWSTokenService;
import cn.ultronxr.system.bean.UserInfo;
import cn.ultronxr.system.bean.mybatis.bean.Role;
import cn.ultronxr.system.bean.mybatis.bean.User;
import cn.ultronxr.system.bean.mybatis.bean.UserRole;
import cn.ultronxr.system.bean.mybatis.mapper.RoleMapper;
import cn.ultronxr.system.bean.mybatis.mapper.UserMapper;
import cn.ultronxr.system.bean.mybatis.mapper.UserRoleMapper;
import cn.ultronxr.system.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/13 14:04
 * @description
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;

    private final RoleMapper roleMapper;

    private final UserRoleMapper userRoleMapper;

    private final JWSTokenService jwsTokenService;

    public UserServiceImpl(
            UserMapper userMapper,
            RoleMapper roleMapper,
            UserRoleMapper userRoleMapper,
            JWSTokenService jwsTokenService
    ) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
        this.jwsTokenService = jwsTokenService;
    }


    @Override
    public List<User> queryUser(User user) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        if(user != null) {
            wrapper.like(StringUtils.isNotEmpty(user.getNick()), User::getNick, user.getNick())
                    .like(StringUtils.isNotEmpty(user.getUsername()), User::getUsername, user.getUsername())
                    .like(StringUtils.isNotEmpty(user.getNote()), User::getNote, user.getNote());
        }
        return userMapper.selectList(wrapper);
    }

    @Override
    public User findUserByUsername(String username) {
        List<User> list = userMapper.selectList(
                Wrappers.lambdaQuery(User.class)
                        .eq(User::getUsername, username)
        );
        if(CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public UserInfo getUserInfo(String jwsToken) {
        JWSParseResult parseResult = jwsTokenService.parseToken(jwsToken);
        Long userId = parseResult.getUserId();
        return getUserInfo(userId);
    }

    @Override
    public UserInfo getUserInfo(Long userId) {
        if(userId == null) {
            return null;
        }
        User user = userMapper.selectById(userId);
        UserInfo userInfo = new UserInfo();
        BeanUtil.copyProperties(user, userInfo, true);

        List<Object> userRoleList = userRoleMapper.selectObjs(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getId()).select(UserRole::getRoleId));
        List<Role> roleList = roleMapper.selectList(new LambdaQueryWrapper<Role>().in(Role::getId, userRoleList));
        userInfo.setRoleList(roleList);
        return userInfo;
    }

}
