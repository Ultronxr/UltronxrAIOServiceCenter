package cn.ultronxr.web.security.handler.success;

import cn.ultronxr.common.util.AjaxResponseUtils;
import cn.ultronxr.common.util.HttpResponseUtils;
import cn.ultronxr.web.security.component.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import static cn.ultronxr.web.bean.Constant.AuthCookieKey.AUTH_KEY;
import static cn.ultronxr.web.bean.Constant.AuthCookieKey.USERNAME_KEY;

/**
 * @author Ultronxr
 * @date 2023/03/14 18:08
 * @description 用户登录认证成功 handler
 */
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private TokenProvider tokenProvider;


    public CustomAuthenticationSuccessHandler(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String authToken = tokenProvider.createAuthToken(authentication);
        log.info("用户登录成功：username = {}\n{}", authentication.getName(), authToken);

        //User user = (User) authentication.getPrincipal();
        //UserCache.putUser(user);

        HashMap<String, String> responseMap = new HashMap<>();
        responseMap.put(AUTH_KEY, authToken);
        //responseMap.put(USERNAME_KEY, authentication.getName());
        HttpResponseUtils.sendJson(response, AjaxResponseUtils.success("用户登录成功", responseMap));
    }

}
