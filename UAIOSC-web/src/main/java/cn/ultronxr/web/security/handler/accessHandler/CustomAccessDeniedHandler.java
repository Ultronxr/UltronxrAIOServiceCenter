package cn.ultronxr.web.security.handler.accessHandler;

import cn.ultronxr.common.util.AjaxResponseUtils;
import cn.ultronxr.common.util.HttpResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Ultronxr
 * @date 2023/03/11 16:50
 * @description 用户鉴权失败 handler
 */
@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        // 当用户在没有授权的情况下访问受保护的REST资源时，将调用此方法发送403 Forbidden响应
        //response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
        log.warn("request = {} , response = {}", request.getRequestURI(), AjaxResponseUtils.HTTP.forbidden());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        HttpResponseUtils.sendJson(response, AjaxResponseUtils.HTTP.forbidden());
    }

}
