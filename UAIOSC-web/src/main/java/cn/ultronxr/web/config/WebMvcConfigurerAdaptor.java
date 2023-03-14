package cn.ultronxr.web.config;

import cn.ultronxr.web.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Ultronxr
 * @date 2023/01/15 14:42
 * @description
 */
@Configuration
public class WebMvcConfigurerAdaptor implements WebMvcConfigurer {


    //@Override
    //public void addInterceptors(InterceptorRegistry registry) {
    //    // 注册拦截器
    //    registry.addInterceptor(authInterceptor())
    //            // 不需要拦截的路径
    //            .excludePathPatterns("/error/**")
    //            .excludePathPatterns("/", "/login", "/index")
    //            .excludePathPatterns("/ajaxLogin")
    //            // 需要拦截的路径
    //            .addPathPatterns("/**");
    //}
    //
    //@Bean
    //public AuthInterceptor authInterceptor(){
    //    return new AuthInterceptor();
    //}

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
