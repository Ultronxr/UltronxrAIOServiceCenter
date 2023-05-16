package cn.ultronxr.scheduled.job;

import org.jetbrains.annotations.NotNull;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * @author Ultronxr
 * @date 2023/04/15 18:43:44
 * @description Job工厂
 */
@Component
public class JobFactory extends AdaptableJobFactory {

    private final AutowireCapableBeanFactory autowireCapableBeanFactory;

    public JobFactory(AutowireCapableBeanFactory autowireCapableBeanFactory) {
        this.autowireCapableBeanFactory = autowireCapableBeanFactory;
    }

    @Override
    protected @NotNull Object createJobInstance(@NotNull TriggerFiredBundle triggerFiredBundle) throws Exception {
        Object jobInstance = super.createJobInstance(triggerFiredBundle);
        autowireCapableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }

}
