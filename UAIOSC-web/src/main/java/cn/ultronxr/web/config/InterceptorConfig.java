package cn.ultronxr.web.config;

import cn.ultronxr.web.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Ultronxr
 * @date 2023/01/15 14:42
 * @description web拦截器配置
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        registry.addInterceptor(authInterceptor())
                // 不需要拦截的路径
                .excludePathPatterns("/login", "/error/**")
                // 需要拦截的路径
                .addPathPatterns("/**");
    }

    @Bean
    public AuthInterceptor authInterceptor(){
        return new AuthInterceptor();
    }

}
