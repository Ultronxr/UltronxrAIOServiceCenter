package cn.ultronxr.web.security.filter;

import cn.ultronxr.web.security.component.TokenProvider;
import cn.ultronxr.web.security.handler.exception.CustomAuthenticationEntryPoint;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cn.ultronxr.web.bean.Constant.AuthCookieKey.AUTH_KEY;

/**
 * @author Ultronxr
 * @date 2023/03/11 16:53
 * @description 自定义请求认证 filter ，拦截除了配置了 permitAll() 之外的所有请求
 */
@Slf4j
public class JWTAuthenticationFilterForRequest extends BasicAuthenticationFilter {

    private final TokenProvider tokenProvider;


    public JWTAuthenticationFilterForRequest(AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
        super(authenticationManager, new CustomAuthenticationEntryPoint());
        //super.setRememberMeServices(rememberMeServices);
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader(AUTH_KEY);
        String token = null;
        if (StringUtils.isNotEmpty(bearerToken) && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.replace("Bearer ", "");
        }

        if (StringUtils.isNotEmpty(token)) {
            Authentication authentication = null;
            try {
                authentication = tokenProvider.parseAuthentication(token);
            } catch (Exception e) {
                log.warn("拦截请求：request = {} , 处理结果 = token 验证失败", request.getRequestURI());
                super.getAuthenticationEntryPoint().commence(request, response, new BadCredentialsException("token invalid !"));
                return;
            }
            if(null != authentication) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

}
