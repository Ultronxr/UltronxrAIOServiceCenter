package cn.ultronxr.web.config;

import cn.ultronxr.web.filter.ReplaceRequestInputStreamFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ultronxr
 * @date 2023/03/14 17:02
 * @description filter 配置
 */
@Configuration
public class FilterConfig {


    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ReplaceRequestInputStreamFilter());
        registration.setOrder(1);
        registration.addUrlPatterns("/*");
        registration.setName("streamFilter");
        return registration;
    }

}
