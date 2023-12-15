package cn.ultronxr.web.filter;

import cn.ultronxr.web.bean.RequestWrapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ultronxr
 * @date 2023/03/14 16:59
 * @description 把 Request 替换成 {@link RequestWrapper}
 */
@Slf4j
public class ReplaceRequestInputStreamFilter implements Filter {

    /**
     *  如果请求的 URI 地址以下列项开头，则不会把 HttpServletRequest 替换成 {@link RequestWrapper}<br/>
     *  为了避免某些情况下，RequestWrapper 中读取了输入流，导致后续的 request.getParameter() 方法无法获取参数（例如 Druid 的登录请求）
     */
    private static final List<String> IGNORE_REQUEST_URI_PREFIX = Arrays.asList("/druid/");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 不拦截文件类型请求
        if(request.getContentType() == null || request.getContentType().contains("multipart/form-data")) {
            chain.doFilter(request, response);
            return;
        }
        // 不拦截指定 URI 请求
        String requestURI = ((HttpServletRequest) request).getRequestURI();
        for(String ignore : IGNORE_REQUEST_URI_PREFIX) {
            if(requestURI.startsWith(ignore)) {
                chain.doFilter(request, response);
                return;
            }
        }
        // 拦截其他请求，把 HttpServletRequest 替换成 RequestWrapper
        ServletRequest requestWrapper = new RequestWrapper((HttpServletRequest) request);
        chain.doFilter(requestWrapper, response);
    }
}
