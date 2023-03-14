package cn.ultronxr.web.filter;

import cn.ultronxr.common.util.AjaxResponseUtils;
import cn.ultronxr.web.bean.LoginObj;
import cn.ultronxr.web.service.TokenProvider;
import cn.ultronxr.common.util.HttpResponseUtils;
import cn.ultronxr.web.service.impl.PersistentTokenBasedRememberMeServicesImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import static cn.ultronxr.web.bean.Constant.AuthCookieKey.AUTH_KEY;
import static cn.ultronxr.web.bean.Constant.AuthCookieKey.USERNAME_KEY;

/**
 * @author Ultronxr
 * @date 2023/03/12 21:08
 * @description 自定义用户登录认证 filter ，只拦截 /ajaxLogin 请求
 */
@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private TokenProvider tokenProvider;

    private UserDetailsService userDetailsServiceImpl;

    private PersistentTokenRepository customPersistentTokenRepository;


    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
                                   TokenProvider tokenProvider,
                                   UserDetailsService userDetailsServiceImpl,
                                   PersistentTokenRepository customPersistentTokenRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.customPersistentTokenRepository = customPersistentTokenRepository;
        // 指定该过滤器拦截的请求
        super.setFilterProcessesUrl("/ajaxLogin");
    }

    /**
     * 对请求的用户进行认证
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginObj loginObj = null;
        try {
            loginObj = new ObjectMapper().readValue(request.getInputStream(), LoginObj.class);
        } catch (IOException e) {
            throw new RuntimeException("LoginObj 读取失败！");
        }
        log.info("用户尝试登录：{}", loginObj);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginObj.getUsername(), loginObj.getPassword());
        return authenticationManager.authenticate(token);
    }

    /**
     * 用户认证成功时执行的方法
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        String authToken = tokenProvider.createAuthToken(authentication);
        log.info("用户登录成功：username = {}\n{}", authentication.getName(), authToken);

        //User user = (User) authentication.getPrincipal();
        //UserCache.putUser(user);

        HashMap<String, String> responseMap = new HashMap<>();
        responseMap.put(AUTH_KEY, authToken);
        responseMap.put(USERNAME_KEY, authentication.getName());
        HttpResponseUtils.sendJson(response, AjaxResponseUtils.success("用户登录成功", responseMap));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        //PersistentTokenBasedRememberMeServices rememberMeServices =
        //        new PersistentTokenBasedRememberMeServicesImpl(UUID.randomUUID().toString(), userDetailsServiceImpl, customPersistentTokenRepository);
        //// 重新设置remember-me参数key
        //rememberMeServices.setParameter("rememberMe");
        ////rememberMeServices.setAlwaysRemember(true);
        //// 重新设置rememberMeServices实现类
        //this.setRememberMeServices(rememberMeServices);

        //super.successfulAuthentication(request, response, chain, authentication);
    }

    /**
     * 用户认证失败时执行的方法
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.warn("用户登录失败，failed = {}", failed.getMessage());
        HttpResponseUtils.sendJson(response, AjaxResponseUtils.HTTP.unauthorized("用户名或密码错误！"));

        //super.unsuccessfulAuthentication(request, response, failed);
    }
}
