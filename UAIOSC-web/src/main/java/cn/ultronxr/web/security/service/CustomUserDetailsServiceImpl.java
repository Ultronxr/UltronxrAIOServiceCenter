package cn.ultronxr.web.security.service;

import cn.ultronxr.system.bean.mybatis.bean.Permission;
import cn.ultronxr.system.bean.mybatis.bean.Role;
import cn.ultronxr.system.bean.mybatis.bean.User;
import cn.ultronxr.system.service.RolePermissionService;
import cn.ultronxr.system.service.UserRoleService;
import cn.ultronxr.system.service.UserService;
import cn.ultronxr.web.security.bean.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ultronxr
 * @date 2023/03/11 10:52
 * @description 对 UserDetailsService 的自定义实现
 */
@Service
@Slf4j
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    private final UserRoleService userRoleService;

    private final RolePermissionService rolePermissionService;

    private final PasswordEncoder customPasswordEncoder;

    public CustomUserDetailsServiceImpl(
            UserService userService,
            UserRoleService userRoleService,
            RolePermissionService rolePermissionService,
            PasswordEncoder customPasswordEncoder
    ) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.rolePermissionService = rolePermissionService;
        this.customPasswordEncoder = customPasswordEncoder;
    }


    /**
     * 从数据库中加载对应用户名的用户信息，包装成 UserDetails 对象（SecurityUser），并添加该用户的权限列表
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUsername(username);
        if(null == user) {
            throw new UsernameNotFoundException("用户不存在！");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        // 查询出用户的角色、权限，添加到权限列表
        List<Role> roleList = userRoleService.getRoleListForUser(user.getId());
        roleList.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getCode()));
            List<Permission> permissionList = rolePermissionService.getPermissionListForRole(role.getId());
            if(CollectionUtils.isNotEmpty(permissionList)) {
                permissionList.stream()
                        .map(permission -> new SimpleGrantedAuthority(permission.getPermissionCode()))
                        .forEach(authorities::add);
            }
        });

        SecurityUser securityUser = new SecurityUser(user);
        securityUser.setPassword(customPasswordEncoder.encode(securityUser.getPassword()));
        securityUser.setAuthorities(authorities);
        securityUser.setUserId(user.getId());

        return securityUser;
    }

}
