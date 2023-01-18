package cn.ultronxr.web.controller;

import cn.ultronxr.common.bean.AjaxResponse;
import cn.ultronxr.framework.cache.user.UserCache;
import cn.ultronxr.framework.jjwt.JWSTokenService;
import cn.ultronxr.system.bean.mybatis.bean.User;
import cn.ultronxr.web.bean.LoginObj;
import cn.ultronxr.web.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

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
    @GetMapping(value = {"", "login"})
    public String login(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        String clientAuthToken = request.getHeader("authToken"),
                clientRefreshToken = request.getHeader("refreshToken");
        if(loginService.isLogin(clientAuthToken, clientRefreshToken)) {
            return "index";
        } else if(loginService.isOnlyAuthTokenExpired(clientAuthToken, clientAuthToken)) {
            String updatedAuthToken = loginService.updateAuthToken(clientAuthToken, clientRefreshToken);
            //response.addCookie();
        }
        return "login";
    }

    /**
     * 处理 ajax login 请求
     */
    @PostMapping("ajaxLogin")
    @ResponseBody
    public AjaxResponse ajaxLogin(@RequestBody LoginObj loginObj, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        loginObj.setRememberMe(loginObj.getRememberMe() != null && loginObj.getRememberMe());

        // 校验用户名和密码
        User user = loginService.login(loginObj);
        if(user != null) {
            HashMap<String, String> responseMap = new HashMap<>();
            String authToken = null,
                    refreshToken = null;

            // 校验成功，在缓存中保存用户信息
            UserCache.putUser(user, loginObj.getRememberMe());

            // 签发 JWS auth token 和 refresh token ，并存入 redis
            authToken = jwsTokenService.createAuthToken(loginObj.getUsername(), loginObj.getRememberMe());
            if(loginObj.getRememberMe()) {
                refreshToken = jwsTokenService.createRefreshToken(loginObj.getUsername(), loginObj.getRememberMe());
                responseMap.put("refreshToken", refreshToken);
            }
            jwsTokenService.saveToken(loginObj.getUsername(), authToken, refreshToken);

            responseMap.put("authToken", authToken);
            //response.addCookie(new Cookie("authToken", authToken).setHttpOnly(true););
            return AjaxResponse.success("用户登录成功", responseMap);
        }

        return AjaxResponse.fail("用户登录失败");
    }

}
