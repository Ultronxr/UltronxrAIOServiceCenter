package cn.ultronxr.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Ultronxr
 * @date 2022/12/10 18:38
 * @description
 */
@SpringBootApplication(scanBasePackages = {
        "cn.ultronxr.common",
        "cn.ultronxr.gameregister",
        "cn.ultronxr.web"
})
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
