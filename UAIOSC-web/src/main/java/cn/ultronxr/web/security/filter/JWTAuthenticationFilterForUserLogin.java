package cn.ultronxr.web.security.filter;

import cn.ultronxr.web.security.handler.authenticationHandler.CustomAuthenticationFailureHandler;
import cn.ultronxr.web.security.handler.authenticationHandler.CustomAuthenticationSuccessHandler;
import cn.ultronxr.web.bean.LoginObj;
import cn.ultronxr.web.bean.RequestWrapper;
import cn.ultronxr.web.security.component.TokenProvider;
import cn.ultronxr.web.security.service.CustomPersistentTokenBasedRememberMeServicesImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static cn.ultronxr.framework.bean.Constant.AuthCookieKey.REMEMBER_ME_KEY;

/**
 * @author Ultronxr
 * @date 2023/03/12 21:08
 * @description 自定义用户登录认证 filter ，只拦截 /ajaxLogin 请求
 */
@Slf4j
public class JWTAuthenticationFilterForUserLogin extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService customUserDetailsServiceImpl;

    private final PersistentTokenRepository customPersistentTokenRepository;


    public JWTAuthenticationFilterForUserLogin(AuthenticationManager authenticationManager,
                                   TokenProvider tokenProvider,
                                   UserDetailsService customUserDetailsServiceImpl,
                                   PersistentTokenRepository customPersistentTokenRepository) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsServiceImpl = customUserDetailsServiceImpl;
        this.customPersistentTokenRepository = customPersistentTokenRepository;

        // 指定该过滤器拦截的请求
        super.setFilterProcessesUrl("/login");
        // 设置 handler
        super.setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandler(tokenProvider));
        super.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler());
    }

    /**
     * 对请求的用户进行认证
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginObj loginObj = null;
        try {
            loginObj = new ObjectMapper().readValue(new RequestWrapper(request).getInputStream(), LoginObj.class);
            //loginObj = new ObjectMapper().readValue(request.getInputStream(), LoginObj.class);
        } catch (IOException e) {
            throw new RuntimeException("LoginObj 读取失败！");
        }
        log.info("用户尝试登录：{}", loginObj);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginObj.getUsername(), loginObj.getPassword());
        return authenticationManager.authenticate(token);
    }

    /**
     * 用户认证成功时执行的方法<br/>
     * 最终会调用 {@link CustomAuthenticationSuccessHandler#onAuthenticationSuccess(HttpServletRequest, HttpServletResponse, Authentication)}
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        // 创建一个自定义持久化 token rememberMe 实现类
        PersistentTokenBasedRememberMeServices rememberMeServices =
                new CustomPersistentTokenBasedRememberMeServicesImpl(UUID.randomUUID().toString(), customUserDetailsServiceImpl, customPersistentTokenRepository);
        // 重新设置 remember-me 参数 key
        rememberMeServices.setParameter(REMEMBER_ME_KEY);
        rememberMeServices.setCookieName(REMEMBER_ME_KEY);
        //rememberMeServices.setUseSecureCookie(true);
        super.setRememberMeServices(rememberMeServices);

        // 走父类的认证成功流程，包括：
        // SecurityContextHolder.setContext()、rememberMeServices、successHandler.onAuthenticationSuccess
        super.successfulAuthentication(request, response, chain, authentication);
    }

    /**
     * 用户认证失败时执行的方法<br/>
     * 最终会调用 {@link CustomAuthenticationFailureHandler#onAuthenticationFailure(HttpServletRequest, HttpServletResponse, AuthenticationException)}
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        // 走父类的认证失败流程，包括 failureHandler.onAuthenticationFailure()
        super.unsuccessfulAuthentication(request, response, failed);
    }

}
