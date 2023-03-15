package cn.ultronxr.web.security.handler.success;

import cn.ultronxr.common.util.AjaxResponseUtils;
import cn.ultronxr.common.util.HttpResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

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
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if(null == authentication) {
            log.warn("用户登出失败：用户未登录！");
            HttpResponseUtils.sendJson(response, AjaxResponseUtils.HTTP.unauthorized("用户未登录！"));
            return;
        }
        log.info("用户登出成功：username = {}", authentication.getName());
        HttpResponseUtils.sendJson(response, AjaxResponseUtils.success("用户登出成功"));
    }

}
