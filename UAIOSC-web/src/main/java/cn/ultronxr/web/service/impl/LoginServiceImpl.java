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
            String username = authParse.getUsername(),
                    serverAuthToken = jwsTokenService.getAuthToken(username);

            if(StringUtils.isNotEmpty(serverAuthToken) && serverAuthToken.equals(clientAuthToken)) {
                // redis 中存储的 server auth token 与 client auth token 匹配
                if(null == user || !user.getUsername().equals(username)) {
                    // 缓存中无 user 或不匹配，重新写入缓存
                    UserCache.deleteUser();
                    UserCache.putUser(userService.findUserByUsername(username));
                }
                return true;
            }
        } else if(StringUtils.isNotEmpty(clientRefreshToken) && authParse.isExpired()) {
            // 如果该用户的 client auth token 验证结果是“已过期”，则检查该用户对应的 refresh token
            // 如果两个 token 都过期，则返回 false
            JWSParseResult refreshParse = jwsTokenService.parseToken(clientRefreshToken);
            if(!refreshParse.isValidation()) {
                return false;
            }
            // 如果只有 auth token 过期，refresh token 未过期，则重新签发 auth token （不在这里面写，另外需要逻辑代码判断）
            return true;
        }

        return false;
    }

    @Override
    public boolean isAuthTokenExpiredButRefreshTokenStillValid(String clientAuthToken, String clientRefreshToken) {
        // 对于 refresh token 的判断需要使用 validation 属性，确保其有效
        return jwsTokenService.expiredToken(clientAuthToken)
                && jwsTokenService.validatedToken(clientRefreshToken);
    }

    @Override
    public String updateAuthToken(String clientAuthToken, String clientRefreshToken) {
        if(isAuthTokenExpiredButRefreshTokenStillValid(clientAuthToken, clientRefreshToken)) {
            JWSParseResult refreshParse = jwsTokenService.parseToken(clientRefreshToken);
            String updatedAuthToken = jwsTokenService.createAuthToken(refreshParse.getUsername());
            jwsTokenService.saveToken(refreshParse.getUsername(), updatedAuthToken, clientRefreshToken);
            log.info("使用 refreshToken 对 authToken 进行更新，username={}", refreshParse.getUsername());
            return updatedAuthToken;
        }
        return null;
    }

    @Override
    public boolean logout(String clientAuthToken) {
        if (StringUtils.isEmpty(clientAuthToken)) {
            return false;
        }

        JWSParseResult authParse = jwsTokenService.parseToken(clientAuthToken);
        String username = authParse.getUsername();
        String cachedUsername = ((User) UserCache.getUser()).getUsername();

        if(username.equals(cachedUsername)) {
            UserCache.deleteUser();
            jwsTokenService.deleteToken(username);
            log.info("用户登出 username = {}", username);
            return true;
        }
        return false;
    }

}
