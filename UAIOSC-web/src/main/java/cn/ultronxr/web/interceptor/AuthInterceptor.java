package cn.ultronxr.web.interceptor;

import cn.ultronxr.common.util.AjaxResponseUtils;
import cn.ultronxr.framework.util.JWSTokenUtils;
import cn.ultronxr.web.service.LoginService;
import cn.ultronxr.web.util.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static cn.ultronxr.framework.bean.Constant.AuthCookieKey.*;
import static cn.ultronxr.framework.bean.Constant.AuthInfo;

/**
 * @author Ultronxr
 * @date 2023/01/15 14:44
 * @description 用户验证拦截器<br/>
 *              判断用户是否已登录，对于系统的操作是否放行
 */
@Deprecated
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;


    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     * 若返回true请求将会继续执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法不拦截 直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String clientAuthToken = null,
                clientRefreshToken = null;
        // 从请求的 cookie 中取出 token
        for(Cookie cookie : request.getCookies()) {
            if(cookie.getName().equals(AUTH_KEY)) {
                clientAuthToken = cookie.getValue();
            }
            if(cookie.getName().equals(REFRESH_KEY)) {
                clientRefreshToken = cookie.getValue();
            }
        }

        // 未携带 auth token ，直接判未登录
        if(StringUtils.isEmpty(clientAuthToken)) {
            log.info("AuthInterceptor 请求地址 = {} | 登录结果 = {}", request.getRequestURI(), AuthInfo.NO_AUTH_TOKEN);
            return notLoginResponse(response);
        }

        clientAuthToken = JWSTokenUtils.unwrapRequestToken(clientAuthToken);
        clientRefreshToken = JWSTokenUtils.unwrapRequestToken(clientRefreshToken);

        if(loginService.isLogin(clientAuthToken, clientRefreshToken)) {
            // 已登录，放行
            if(loginService.isAuthTokenExpiredButRefreshTokenStillValid(clientAuthToken, clientRefreshToken)) {
                // 已登录但 auth token 过期，使用 refresh token 对 auth token 进行更新
                String updatedAuthToken = loginService.updateAuthToken(clientAuthToken, clientRefreshToken);
                response.addCookie(CookieUtils.commonCookie(AUTH_KEY, updatedAuthToken));
                log.info("AuthInterceptor 请求地址 = {} | 登录结果 = {}", request.getRequestURI(), AuthInfo.REFRESH_AUTH_TOKEN);
            }
            log.info("AuthInterceptor 请求地址 = {} | 登录结果 = {}", request.getRequestURI(), AuthInfo.LOGGED_IN);
            return true;
        }

        log.info("AuthInterceptor 请求地址 = {} | 登录结果 = {}", request.getRequestURI(), AuthInfo.INVALID_TOKEN);
        return notLoginResponse(response);
    }

    private boolean notLoginResponse(HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter pw = response.getWriter();
        pw.write(AjaxResponseUtils.HTTP.unauthorized("用户登录验证失败").toString());
        response.sendRedirect("/login");

        return false;
    }

    /***
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /***
     * 整个请求结束之后被调用，也就是在DispatchServlet渲染了对应的视图之后执行（主要用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
