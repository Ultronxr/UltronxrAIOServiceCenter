package cn.ultronxr.web.security.handler.exception;

import cn.ultronxr.common.util.AjaxResponseUtils;
import cn.ultronxr.common.util.HttpResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Ultronxr
 * @date 2023/03/11 16:51
 * @description 用户认证失败 handler
 */
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // 当用户尝试访问安全的REST资源而不提供任何凭据时，将调用此方法发送401响应
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException == null ? "Unauthorized" : authException.getMessage());
        log.warn("request = {} , response = {}", request.getRequestURI(), AjaxResponseUtils.HTTP.unauthorized());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        HttpResponseUtils.sendJson(response, AjaxResponseUtils.HTTP.unauthorized());
    }

}
