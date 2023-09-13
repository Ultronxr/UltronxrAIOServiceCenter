package cn.ultronxr.web.security.config;

import cn.ultronxr.web.security.handler.exception.CustomAccessDeniedHandler;
import cn.ultronxr.web.security.handler.exception.CustomAuthenticationEntryPoint;
import cn.ultronxr.web.security.filter.JWTAuthenticationFilterForUserLogin;
import cn.ultronxr.web.security.filter.JWTAuthenticationFilterForRequest;
import cn.ultronxr.web.filter.ReplaceRequestInputStreamFilter;
import cn.ultronxr.web.security.component.TokenProvider;
import cn.ultronxr.web.security.handler.success.CustomLogoutSuccessHandler;
import cn.ultronxr.web.security.service.CustomPersistentTokenBasedRememberMeServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.filter.CorsFilter;

import java.util.UUID;

import static cn.ultronxr.web.bean.Constant.AuthCookieKey.AUTH_KEY;
import static cn.ultronxr.web.bean.Constant.AuthCookieKey.REMEMBER_ME_KEY;

/**
 * @author Ultronxr
 * @date 2023/03/11 10:49
 * @description SpringSecurity 配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfigOldVer extends WebSecurityConfigurerAdapter {

    @Autowired
    private CorsFilter corsFilter;

    @Autowired
    private UserDetailsService customUserDetailsServiceImpl;

    @Autowired
    private PasswordEncoder customPasswordEncoder;

    @Autowired
    private PersistentTokenRepository customPersistentTokenRepository;

    @Autowired
    private TokenProvider tokenProvider;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsServiceImpl)
                .passwordEncoder(customPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 禁用 CSRF
                .csrf().disable()
                // 不创建且不使用会话，前后端分离无状态（这会导致有关功能失效，例如 基于 session cookie 的 remember-me）
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                // 防止 iframe 造成跨域
                .and()
                .headers().frameOptions().disable();

        // 添加自定义 filter
        httpSecurity
                // 解决跨域问题
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                // 解决 request inputStream 流只能读取一次的问题
                .addFilterBefore(new ReplaceRequestInputStreamFilter(), corsFilter.getClass())
                // 自定义用户登录认证 filter
                .addFilterAt(
                        new JWTAuthenticationFilterForUserLogin(authenticationManagerBean(), tokenProvider, customUserDetailsServiceImpl, customPersistentTokenRepository),
                        UsernamePasswordAuthenticationFilter.class
                )
                // 自定义请求认证 filter
                .addFilterAt(
                        new JWTAuthenticationFilterForRequest(authenticationManagerBean(), tokenProvider),
                        UsernamePasswordAuthenticationFilter.class
                );

        // 拦截请求配置
        httpSecurity
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/static/**", "/file/**").permitAll()
                .antMatchers("/druid/**").permitAll()
                .antMatchers("/login", "/ajaxLogin", "/ajaxLogout").permitAll()
                .antMatchers("/wechat/push").permitAll()
                .anyRequest().authenticated();

        // 开启用户登录功能
        httpSecurity
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/ajaxLogin");
        // 在这里写如下配置是无效的！
        // 因为 JWTAuthenticationFilterForUserLogin 自定义实现了 AbstractAuthenticationProcessingFilter ，
        // 这里的配置会被自定义Filter覆盖，所以需要进入自定义Filter构造中调用 super.setXX() 方法进行设置
        //
        //        .successHandler(new CustomAuthenticationSuccessHandler(tokenProvider))
        //        .failureHandler(new CustomAuthenticationFailureHandler());

        // 开启“记住我”功能
        httpSecurity
                .rememberMe()
                .rememberMeParameter(REMEMBER_ME_KEY)
                .rememberMeCookieName(REMEMBER_ME_KEY);
        // 同上理，下面这些配置会被自定义Filter覆盖，所以需要进入自定义Filter构造中手动设置
        //        .rememberMeServices(customRememberMeServices);

        // 配置用户登出功能
        httpSecurity
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                .invalidateHttpSession(true)
                .deleteCookies(AUTH_KEY, REMEMBER_ME_KEY);

        // 自定义异常处理
        httpSecurity
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .accessDeniedHandler(new CustomAccessDeniedHandler());

    }

    /**
     * 主动暴露 AuthenticationManager Bean ，防止依赖注入时为 null
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //@Bean
    //public GrantedAuthorityDefaults grantedAuthorityDefaults() {
    //    // 去除 ROLE_ 前缀
    //    return new GrantedAuthorityDefaults("");
    //}

}
