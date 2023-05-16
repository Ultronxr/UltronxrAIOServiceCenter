package cn.ultronxr.scheduled.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

/**
 * @author Ultronxr
 * @date 2023/04/15 19:10:38
 * @description 缺省Job对象。当任务的执行Job未指定时，默认执行该Job。
 */
@Component
@Slf4j
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class DefaultJob implements Job {


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        String jobName = jobDetail.getKey().getName(),
                jobGroup = context.getJobDetail().getKey().getGroup();
        log.info("[Quartz] DefaultJob({}, {}) 执行。", jobName, jobGroup);
    }

}
