package cn.ultronxr.web.service.impl;

import cn.ultronxr.framework.bean.JWSParseResult;
import cn.ultronxr.framework.cache.user.UserCache;
import cn.ultronxr.framework.jjwt.JWSTokenService;
import cn.ultronxr.system.bean.mybatis.bean.User;
import cn.ultronxr.system.service.UserService;
import cn.ultronxr.web.bean.LoginObj;
import cn.ultronxr.web.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ultronxr
 * @date 2023/01/13 15:12
 * @description
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserService userService;

    @Autowired
    private JWSTokenService jwsTokenService;


    @Override
    public User login(LoginObj loginObj) {
        User user = userService.findUserByUsername(loginObj.getUsername());
        if(null != user && user.getPassword().equals(loginObj.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public boolean isLogin(String clientAuthToken, String clientRefreshToken) {
        // 特判：请求未携带 client auth token ，一律作为未登录处理
        if(StringUtils.isEmpty(clientAuthToken)) {
            // 删除缓存中的用户信息
            UserCache.deleteUser();
            return false;
        }

        User user = (User) UserCache.getUser();

        // 验证请求中携带的 client auth token
        JWSParseResult authParse = jwsTokenService.parseToken(clientAuthToken);

        if(authParse.isValidation()) {
            // client auth token 验证通过，进一步检查
            boolean rememberMe = authParse.getRememberMe();
            String username = authParse.getUsername(),
                    serverAuthToken = jwsTokenService.getAuthToken(username);

            if(serverAuthToken.equals(clientAuthToken)) {
                // redis 中存储的 server auth token 与 client auth token 匹配
                if(null == user || !user.getUsername().equals(username)) {
                    // 缓存中无 user 或不匹配，重新写入缓存
                    UserCache.deleteUser();
                    UserCache.putUser(userService.findUserByUsername(username), rememberMe);
                }
                return true;
            }
        } else if(StringUtils.isNotEmpty(clientRefreshToken) && authParse.isExpired()) {
            // 如果该用户是“记住我”状态，且 client auth token 验证结果是已过期，则检查该用户对应的 refresh token
            // 如果两个 token 都过期，则返回 false
            JWSParseResult refreshParse = jwsTokenService.parseToken(clientRefreshToken);
            if(!refreshParse.isValidation()) {
                return false;
            }
            // 如果只有 auth token 过期，refresh token 未过期，则重新签发 auth token （不在这里面写，另外需要逻辑代码判断）
        }

        return false;
    }

    @Override
    public boolean isOnlyAuthTokenExpired(String clientAuthToken, String clientRefreshToken) {
        return jwsTokenService.expiredToken(clientAuthToken)
                && !jwsTokenService.expiredToken(clientRefreshToken);
    }

    @Override
    public String updateAuthToken(String clientAuthToken, String clientRefreshToken) {
        if(isOnlyAuthTokenExpired(clientAuthToken, clientRefreshToken)) {
            JWSParseResult authParse = jwsTokenService.parseToken(clientAuthToken);
            return jwsTokenService.createAuthToken(authParse.getUsername(), authParse.getRememberMe());
        }
        return null;
    }

    @Override
    public void logout(LoginObj loginObj) {
        UserCache.deleteUser();
        jwsTokenService.deleteToken(loginObj.getUsername());
    }

}
