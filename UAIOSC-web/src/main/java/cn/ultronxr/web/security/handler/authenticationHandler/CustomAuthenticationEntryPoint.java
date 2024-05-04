package cn.ultronxr.web.security.handler.authenticationHandler;

import cn.ultronxr.common.util.AjaxResponseUtils;
import cn.ultronxr.common.util.HttpResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author Ultronxr
 * @date 2023/03/11 16:51
 * @description 用户认证失败 handler
 */
@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Value("${server.servlet.context-path}")
    public String serverServletContextPath;


    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        String uri = request.getRequestURI().replaceFirst(serverServletContextPath, "");
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();

        // 检查请求的 URI 的对应处理方法（controller）是否存在，如果不存在，直接返回 404 （SpringSecurity 默认返回 401）
        boolean isContain = handlerMethodMap.keySet()
                .stream().anyMatch(info -> info.getPatternValues().contains(uri));
        if(!isContain) {
            log.warn("request = {} , response = {}", request.getRequestURI(), AjaxResponseUtils.HTTP.notFound());
            response.setStatus(HttpStatus.NOT_FOUND.value());
            HttpResponseUtils.sendJson(response, AjaxResponseUtils.HTTP.notFound());
            return;
        }

        // 当用户尝试访问安全的REST资源而不提供任何凭据时，将调用此方法发送401响应
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException == null ? "Unauthorized" : authException.getMessage());
        log.warn("request = {} , response = {}", request.getRequestURI(), AjaxResponseUtils.HTTP.unauthorized());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        HttpResponseUtils.sendJson(response, AjaxResponseUtils.HTTP.unauthorized());
    }

}
