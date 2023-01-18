package cn.ultronxr.web.service;

import cn.ultronxr.system.bean.mybatis.bean.User;
import cn.ultronxr.web.bean.LoginObj;

/**
 * @author Ultronxr
 * @date 2023/01/13 15:12
 * @description
 */
public interface LoginService {

    /**
     * 用户登录逻辑，只对用户名和密码进行校验
     *
     * @param loginObj 用户登录时请求的数据封装
     * @return 校验成功：返回数据库查询出来的对应 {@code User} 对象<br/>
     *          校验失败：返回 {@code null}
     */
    User login(LoginObj loginObj);

    /**
     * 检查用户是否已登录<br/>
     * 注：该方法不适用于 /ajaxLogin 请求，即用户首次登录时的判断
     *
     * @param clientAuthToken    客户端请求的 JWS auth token
     * @param clientRefreshToken 客户端请求的 JWS refresh token
     * @return {@code true} - 已登录；{@code false} - 未登录
     */
    boolean isLogin(String clientAuthToken, String clientRefreshToken);

    /**
     * 检查用户是否：登录已过期，但是 JWS refresh token 仍有效<br/>
     * 仅适用于用户登录时勾选了“记住我”
     *
     * @param clientAuthToken    客户端请求的 JWS auth token
     * @param clientRefreshToken 客户端请求的 JWS refresh token
     * @return
     */
    boolean isOnlyAuthTokenExpired(String clientAuthToken, String clientRefreshToken);

    /**
     * 当用户登录已过期，但是 JWS refresh token 仍有效时，为其签发新的 JWS auth token
     *
     * @param clientAuthToken    客户端请求的 JWS auth token
     * @param clientRefreshToken 客户端请求的 JWS refresh token
     * @return 新的 JWS auth token
     */
    String updateAuthToken(String clientAuthToken, String clientRefreshToken);

    /**
     * 用户主动注销
     *
     * @param loginObj
     */
    void logout(LoginObj loginObj);

}
