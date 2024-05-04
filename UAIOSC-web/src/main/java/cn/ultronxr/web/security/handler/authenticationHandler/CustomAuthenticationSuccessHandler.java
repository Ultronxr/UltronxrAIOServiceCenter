package cn.ultronxr.web.security.handler.authenticationHandler;

import cn.ultronxr.common.util.AjaxResponseUtils;
import cn.ultronxr.common.util.HttpResponseUtils;
import cn.ultronxr.system.bean.mybatis.bean.User;
import cn.ultronxr.web.security.component.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Ultronxr
 * @date 2023/03/14 18:08
 * @description 用户登录认证成功 handler
 */
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;


    public CustomAuthenticationSuccessHandler(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String authToken = tokenProvider.createAuthToken(authentication);
        log.info("用户登录成功：username = {}\n{}", authentication.getName(), authToken);

        User user = (User) authentication.getPrincipal();
        //UserCache.putUser(user);
         authentication.getAuthorities();

        HashMap<String, String> responseMap = new HashMap<>();
        //responseMap.put(USERNAME_KEY, authentication.getName());
        //responseMap.put(AUTH_KEY, authToken);
        responseMap.put("token", authToken);
        responseMap.put("userId", String.valueOf(user.getId()));

        HttpResponseUtils.sendJson(response, AjaxResponseUtils.success("用户登录成功", responseMap));
    }

}
