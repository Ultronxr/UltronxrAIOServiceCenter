package cn.ultronxr.wechat.publicPlatform.runner;

import cn.ultronxr.wechat.publicPlatform.token.AccessTokenManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Ultronxr
 * @date 2023/09/20 13:54:51
 * @description ApplicationRunner 启动器，与 SpringBoot Application 一起运行
 */
@Component
@Order(Integer.MAX_VALUE)
@Slf4j
public class WechatPublicPlatformApplicationRunner implements org.springframework.boot.ApplicationRunner {

    private final AccessTokenManager accessTokenManager;

    WechatPublicPlatformApplicationRunner(@Autowired AccessTokenManager accessTokenManager) {
        this.accessTokenManager = accessTokenManager;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("======项目启动 ApplicationRunner 自动执行开始======");
        accessTokenManager.refreshAccessToken();
        log.info("======项目启动 ApplicationRunner 自动执行完毕======");
    }

}
