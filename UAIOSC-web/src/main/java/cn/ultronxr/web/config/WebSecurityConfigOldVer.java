package cn.ultronxr.web.config;

import cn.ultronxr.web.authExceptionHandler.CustomAccessDeniedHandler;
import cn.ultronxr.web.authExceptionHandler.CustomAuthenticationEntryPoint;
import cn.ultronxr.web.filter.JWTAuthenticationFilter;
import cn.ultronxr.web.filter.JWTAuthorizeFilter;
import cn.ultronxr.web.service.TokenProvider;
import cn.ultronxr.web.service.impl.CustomUserDetailsServiceImpl;
import cn.ultronxr.web.service.impl.PersistentTokenBasedRememberMeServicesImpl;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.filter.CorsFilter;

import java.util.UUID;

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
    private PersistentTokenRepository customPersistentTokenRepository;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    private CustomUserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsServiceImpl)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 禁用 CSRF
                .csrf().disable()
                // 不创建且不使用会话，前后端分离无状态（这会导致有关功能失效，例如 基于 session cookie 的 remember-me）
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 防止 iframe 造成跨域
                .and()
                .headers().frameOptions().disable();

        // 自定义异常处理
        httpSecurity
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler);

        // 添加自定义 filter
        httpSecurity
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilter(new JWTAuthenticationFilter(authenticationManagerBean(), tokenProvider, userDetailsServiceImpl, customPersistentTokenRepository))
                .addFilter(new JWTAuthorizeFilter(authenticationManagerBean(), tokenProvider));

        // 拦截请求配置
        httpSecurity
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/static/**", "/file/**").permitAll()
                .antMatchers("/druid/**").permitAll()
                .antMatchers("/login", "/ajaxLogin", "/ajaxLogout").permitAll()
                .anyRequest().authenticated();

        httpSecurity
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/ajaxLogin");
        //        .successForwardUrl("/index");

        // 开启“记住我”功能
        //httpSecurity
        //        .rememberMe()
        //        //.rememberMeParameter("rememberMe")
        //        //.useSecureCookie(true)
        //        .alwaysRemember(true)
        //        .rememberMeServices(customRememberMeServices())
        //        .userDetailsService(userDetailsServiceImpl)
        //        .tokenRepository(customPersistentTokenRepository);
    }

    //@Bean
    //public RememberMeServices customRememberMeServices() {
    //    PersistentTokenBasedRememberMeServices rememberMeServices =
    //            new PersistentTokenBasedRememberMeServicesImpl(UUID.randomUUID().toString(), userDetailsServiceImpl, customPersistentTokenRepository);
    //    // 重新设置remember-me参数key
    //    rememberMeServices.setParameter("rememberMe");
    //    //rememberMeServices.setAlwaysRemember(true);
    //    // 重新设置rememberMeServices实现类
    //    //this.setRememberMeServices(rememberMeServices);
    //    return rememberMeServices;
    //}

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
