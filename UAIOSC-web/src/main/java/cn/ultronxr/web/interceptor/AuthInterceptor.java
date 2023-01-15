package cn.ultronxr.web.interceptor;

import cn.ultronxr.common.bean.AjaxResponse;
import cn.ultronxr.framework.cache.user.UserCacheService;
import cn.ultronxr.framework.jjwt.TokenService;
import cn.ultronxr.system.bean.mybatis.bean.User;
import cn.ultronxr.system.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author Ultronxr
 * @date 2023/01/15 14:44
 * @description 用户验证拦截器<br/>
 *              判断用户是否已登录，对于系统的操作是否放行
 */
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserCacheService userCacheService;

    @Autowired
    private UserService userService;


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

        // 验证 header 中的 token
        String authToken = request.getHeader("Authorization").replaceFirst("Bearer ", "");
                //refreshToken = request.getHeader("Authorization-Refresh").replaceFirst("Bearer ", "");
        if(StringUtils.isNotEmpty(authToken)) {
            Jws<Claims> jws = tokenService.validateToken(authToken);
            if(null != jws) {
                // token 验证成功，把用户信息存储在 ThreadLocal
                User user = userService.findUserByUsername((String) jws.getBody().get("username"));
                userCacheService.putUser(user, (boolean) jws.getBody().get("rememberMe"));
                return true;
            }
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter pw = response.getWriter();
        pw.write(AjaxResponse.fail("用户登录验证失败").toString());

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
