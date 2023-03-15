package cn.ultronxr.web.filter;

import cn.ultronxr.web.bean.RequestWrapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Ultronxr
 * @date 2023/03/14 16:59
 * @description 把 Request 替换成 {@link RequestWrapper}
 */
@Slf4j
public class ReplaceRequestInputStreamFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = new RequestWrapper((HttpServletRequest) request);
        chain.doFilter(requestWrapper, response);
    }
}
