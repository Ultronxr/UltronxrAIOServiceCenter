package cn.ultronxr.framework.shiro.realm;

import cn.ultronxr.framework.service.LoginService;
import cn.ultronxr.system.bean.mybatis.bean.Permission;
import cn.ultronxr.system.bean.mybatis.bean.Role;
import cn.ultronxr.system.bean.mybatis.bean.User;
import cn.ultronxr.system.service.RolePermissionService;
import cn.ultronxr.system.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/12 18:17
 * @description 自定义认证与授权器
 */
@Slf4j
public class AuthRealm extends AuthorizingRealm {

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RolePermissionService rolePermissionService;

    @Resource
    private LoginService loginService;


    /**
     * 用户登录认证流程
     *
     * @param authenticationToken
     *          subject （用户）提交登录请求时生成的认证 {@code AuthenticationToken}
     * @return
     *          包含对应正确 subject 信息的 {@code SimpleAuthenticationInfo}
     * @throws AuthenticationException
     *          认证过程中产生的异常，抛出任意一种异常都会导致认证失败，认证流程即终止
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken userPwdToken = (UsernamePasswordToken) authenticationToken;
        String username = userPwdToken.getUsername();
        String password = userPwdToken.getPassword() == null ? "" : new String(userPwdToken.getPassword());

        User user = loginService.login(username, password);

        // 注意 return 的密码是数据库中查询出来的正确密码，用于在 CredentialMatcher 中进行比对
        return new SimpleAuthenticationInfo(user, user.getPassword(), this.getClass().getName());
    }

    /**
     * （登录认证成功后）授权流程
     *
     * @param principals the primary identifying principals of the AuthorizationInfo that should be retrieved.
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) principals.fromRealm(this.getClass().getName()).iterator().next();
        List<String> finalPermissionList = new ArrayList<>();
        List<String> finalRoleNameList = new ArrayList<>();

        List<Role> roleList = userRoleService.getRoleListForUser(user.getId());
        if(CollectionUtils.isNotEmpty(roleList)) {
            for(Role role : roleList) {
                finalRoleNameList.add(role.getName());
                List<Permission> permissionList = rolePermissionService.getPermissionListForRole(role.getId());
                if(CollectionUtils.isNotEmpty(permissionList)) {
                    for(Permission perm : permissionList) {
                        finalPermissionList.add(perm.getPermissionName());
                    }
                }
            }
        }
        //Set<Role> roleSet = user.getRoles();
        //if (CollectionUtils.isNotEmpty(roleSet)) {
        //    for (Role role : roleSet) {
        //        roleNameList.add(role.getRname());
        //        Set<Permission> permissionSet = role.getPermissions();
        //        if (CollectionUtils.isNotEmpty(permissionSet)) {
        //            for (Permission permission : permissionSet) {
        //                permissionList.add(permission.getName());
        //            }
        //        }
        //    }
        //}
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(finalPermissionList);
        info.addRoles(finalRoleNameList);
        return info;
    }

}
