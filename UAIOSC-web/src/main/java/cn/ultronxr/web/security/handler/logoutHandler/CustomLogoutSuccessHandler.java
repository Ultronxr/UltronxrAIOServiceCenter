package cn.ultronxr.web.security.handler.logoutHandler;

import cn.ultronxr.common.util.AjaxResponseUtils;
import cn.ultronxr.common.util.HttpResponseUtils;
import cn.ultronxr.framework.bean.JWSParseResult;
import cn.ultronxr.framework.jjwt.JWSTokenService;
import cn.ultronxr.framework.util.JWSTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Ultronxr
 * @date 2023/03/15 18:43
 * @description 用户登出成功 handler
 */
@Slf4j
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final JWSTokenService jwsTokenService;

    public CustomLogoutSuccessHandler(JWSTokenService jwsTokenService) {
        this.jwsTokenService = jwsTokenService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 清除权限
        // 实际上这行代码的操作是多余的，前后端分离不基于 session
        // 在认证链走完之后， SecurityContextHolder.getContext().getAuthentication() 拿到的值恒为 null （即这个方法的入参 authentication = null）
        // 也就是说后续想要获取登录用户的信息只能通过 token
        SecurityContextHolder.clearContext();

        // 通过 token 解析出当前登出的用户
        String token = JWSTokenUtils.unwrapRequestToken(request);
        JWSParseResult parseResult = jwsTokenService.parseToken(token);
        String username = parseResult.getUsername();
        log.info("用户登出成功：username = {}", username);
        HttpResponseUtils.sendJson(response, AjaxResponseUtils.success("用户登出成功"));
    }

}
