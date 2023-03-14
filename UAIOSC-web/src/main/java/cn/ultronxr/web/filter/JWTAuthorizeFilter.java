package cn.ultronxr.web.filter;

import cn.ultronxr.web.service.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.RemoteException;

/**
 * @author Ultronxr
 * @date 2023/03/11 16:53
 * @description 自定义用户鉴权 filter ，拦截除了配置了 permitAll() 之外的所有请求
 */
@Slf4j
public class JWTAuthorizeFilter extends BasicAuthenticationFilter {

    private TokenProvider tokenProvider;


    public JWTAuthorizeFilter(AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
        super(authenticationManager);
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");
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
                throw new RemoteException("token invalid !");
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
