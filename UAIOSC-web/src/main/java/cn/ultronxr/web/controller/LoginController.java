package cn.ultronxr.web.controller;

import cn.ultronxr.common.bean.AjaxResponse;
import cn.ultronxr.common.util.AjaxResponseUtils;
import cn.ultronxr.framework.cache.user.UserCache;
import cn.ultronxr.framework.jjwt.JWSTokenService;
import cn.ultronxr.framework.util.JWSTokenUtils;
import cn.ultronxr.system.bean.mybatis.bean.User;
import cn.ultronxr.web.bean.LoginObj;
import cn.ultronxr.web.service.LoginService;
import cn.ultronxr.web.util.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

import static cn.ultronxr.web.bean.Constant.AuthCookieKey.*;
import static cn.ultronxr.web.bean.Constant.AuthInfo;

/**
 * @author Ultronxr
 * @date 2023/01/13 14:58
 * @description
 */
@Controller
@RequestMapping("/")
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private JWSTokenService jwsTokenService;


    /**
     * 默认返回 login 页面<br/>
     * 如果用户已登录，则跳转到 index 页面
     */
    @GetMapping(value = {"", "login", "index"})
    public String login(@CookieValue(value = AUTH_KEY, required = false) String clientAuthToken,
                        @CookieValue(value = REFRESH_KEY, required = false) String clientRefreshToken,
                        HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        // 未携带 auth token ，直接判未登录
        if(StringUtils.isEmpty(clientAuthToken)) {
            log.info("LoginController 登录结果 = {}", AuthInfo.NO_AUTH_TOKEN);
            return "login";
        }

        clientAuthToken = JWSTokenUtils.unwrapRequestToken(clientAuthToken);
        clientRefreshToken = JWSTokenUtils.unwrapRequestToken(clientRefreshToken);

        if(loginService.isLogin(clientAuthToken, clientRefreshToken)) {
            // 已登录，跳转主页
            if(loginService.isAuthTokenExpiredButRefreshTokenStillValid(clientAuthToken, clientRefreshToken)) {
                // 已登录但 auth token 过期，使用 refresh token 对 auth token 进行更新
                String updatedAuthToken = loginService.updateAuthToken(clientAuthToken, clientRefreshToken);
                response.addCookie(CookieUtils.commonCookie(AUTH_KEY, updatedAuthToken));
                log.info("LoginController 登录结果 = {}",  AuthInfo.REFRESH_AUTH_TOKEN);
            }
            log.info("LoginController 登录结果 = {}", AuthInfo.LOGGED_IN);
            return "index";
        }

        log.info("LoginController 登录结果 = {}", AuthInfo.INVALID_TOKEN);
        return "login";
    }

    /**
     * 处理 ajax login 请求
     */
    @PostMapping("ajaxLogin")
    @ResponseBody
    public AjaxResponse ajaxLogin(@RequestBody LoginObj loginObj, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        // rememberMe 若为 null ，则设 false
        loginObj.setRememberMe(loginObj.getRememberMe() != null && loginObj.getRememberMe());

        // 校验用户名和密码
        User user = loginService.login(loginObj);
        if(user != null) {
            HashMap<String, String> responseMap = new HashMap<>();
            String authToken = null,
                    refreshToken = null;

            // 校验成功，在缓存中保存用户信息
            UserCache.putUser(user);

            // 签发 JWS auth token 和 refresh token ，并存入 redis
            authToken = jwsTokenService.createAuthToken(loginObj.getUsername());
            if(loginObj.getRememberMe()) {
                refreshToken = jwsTokenService.createRefreshToken(loginObj.getUsername());
                responseMap.put(REFRESH_KEY, refreshToken);
            }
            jwsTokenService.saveToken(loginObj.getUsername(), authToken, refreshToken);

            responseMap.put(AUTH_KEY, authToken);
            responseMap.put(USERNAME_KEY, user.getUsername());

            return AjaxResponseUtils.success("用户登录成功", responseMap);
        }

        return AjaxResponseUtils.unauthorized();
    }

    /**
     * 处理 ajax logout 请求
     */
    @GetMapping("ajaxLogout")
    @ResponseBody
    public AjaxResponse ajaxLogout(@CookieValue(value = AUTH_KEY, required = false) String clientAuthToken,
                                   HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        if(loginService.logout(JWSTokenUtils.unwrapRequestToken(clientAuthToken))) {
            return AjaxResponseUtils.success("用户登出成功");
        }
        return AjaxResponseUtils.unauthorized("用户登出失败");
    }

}
