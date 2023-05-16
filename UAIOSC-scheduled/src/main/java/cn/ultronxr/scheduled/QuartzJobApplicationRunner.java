package cn.ultronxr.scheduled;

import cn.ultronxr.scheduled.service.QuartzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Ultronxr
 * @date 2023/04/15 18:52:17
 * @description ApplicationRunner 启动器，与 SpringBoot Application 一起运行
 */
@Component
@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
public class QuartzJobApplicationRunner implements ApplicationRunner {

    private final QuartzService quartzService;

    public QuartzJobApplicationRunner(QuartzService quartzService) {
        this.quartzService = quartzService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        quartzService.initJobsAndStartScheduler();
    }

}
