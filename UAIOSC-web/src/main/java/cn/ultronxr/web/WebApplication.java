package cn.ultronxr.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Ultronxr
 * @date 2022/12/10 18:38
 * @description
 */
@SpringBootApplication(scanBasePackages = {
        "cn.ultronxr.common",
        "cn.ultronxr.framework",
        "cn.ultronxr.system",
        "cn.ultronxr.web",
        "cn.ultronxr.gameregister"
})
@MapperScan(basePackages = {
        "cn.ultronxr.common.bean.mybatis.mapper",
        "cn.ultronxr.framework.bean.mybatis.mapper",
        "cn.ultronxr.system.bean.mybatis.mapper",
        "cn.ultronxr.web.bean.mybatis.mapper",
        "cn.ultronxr.gameregister.bean.mybatis.mapper"
})
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
