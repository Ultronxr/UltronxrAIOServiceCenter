package cn.ultronxr.scheduled.service;

import cn.ultronxr.scheduled.bean.QuartzTask;
import cn.ultronxr.scheduled.bean.QuartzTaskStatus;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2023/04/15 18:46:49
 * @description
 */
@Service
@Slf4j
public class QuartzServiceImpl implements QuartzService {

    private final Scheduler scheduler;

    public QuartzServiceImpl(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void initJobsAndStartScheduler() {
        // 获取所有任务并把每个任务加入调度器
        //List<QuartzTask> taskList = taskMapper.selectByExample(null);
        //for(QuartzTask task : taskList) {
        //    if(addJob(task)) {
        //        log.info("[Quartz] 添加 QuartzJob({}, {}) 成功。", task.getTaskName(), task.getTaskGroup());
        //    } else {
        //        log.info("[Quartz] 添加 QuartzJob({}, {}) 失败！", task.getTaskName(), task.getTaskGroup());
        //    }
        //}
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            log.error("[Quartz] Scheduler 启动失败！", e);
        }
    }

    @Override
    public boolean addJob(QuartzTask task) {
        // 反射获取QuartzJob任务对应的Job类
        Class<? extends Job> jobClass = null;
        try {
            jobClass = (Class<? extends Job>) (Class.forName(task.getTaskClass()).getDeclaredConstructor().newInstance().getClass());
        } catch (Exception ex) {
            ex.printStackTrace();
            log.warn("[Quartz] 反射创建QuartzJob的Job类抛出异常！");
        }
        if(jobClass == null) {
            return false;
        }
        // 向 scheduler 添加任务
        try {
            if(scheduler.checkExists(new JobKey(task.getTaskName(), task.getTaskGroup()))) {
                log.warn("[Quartz] QuartzJob({}, {}) 已存在，无法重复添加！", task.getTaskName(), task.getTaskGroup());
                return false;
            }
            JobDetail jobDetail = JobBuilder
                    .newJob(jobClass)
                    .withIdentity(task.getTaskName(), task.getTaskGroup())
                    .withDescription(task.getTaskDescription())
                    .build();
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity(task.getTaskName(), task.getTaskGroup())
                    .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                    .withSchedule(CronScheduleBuilder.cronSchedule(task.getTaskCron()))
                    .build();

            // 根据QuartzJob任务的状态配置Job是否启动
            if(QuartzTaskStatus.isActivated(task.getStatus())) {
                trigger.getTriggerBuilder().startNow().build();
            } else if(QuartzTaskStatus.isPaused(task.getStatus())) {
                trigger.getTriggerBuilder().startAt(task.getPauseTimeLimit()).build();
            } else if(QuartzTaskStatus.isStopped(task.getStatus())) {
                // Throw Exception: End time cannot be before start time
                //trigger.getTriggerBuilder().endAt(new Date()).build();
            }

            scheduler.scheduleJob(jobDetail, trigger);
            //if(!scheduler.isShutdown() && !scheduler.isStarted()) {
            //    // 这两个if条件方法并不会返回scheduler的当前状态，而是表示曾经是否已经shutdown过了、曾经是否已经start过了。参见其JavaDoc。
            //    scheduler.start();
            //}
        } catch (SchedulerException ex) {
            ex.printStackTrace();
            log.warn("[Quartz] 添加 QuartzJob({}, {}) 时抛出异常！", task.getTaskName(), task.getTaskGroup());
            return false;
        }
        return true;
    }

    @Override
    public boolean stopJob(QuartzTask task) {
        try {
            scheduler.deleteJob(new JobKey(task.getTaskName(), task.getTaskGroup()));
        } catch (SchedulerException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean pauseJob(QuartzTask task) {
        try {
            scheduler.pauseJob(new JobKey(task.getTaskName(), task.getTaskGroup()));
        } catch (SchedulerException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Boolean resumeJob(QuartzTask task) {
        try {
            scheduler.resumeJob(new JobKey(task.getTaskName(), task.getTaskGroup()));
        } catch (SchedulerException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

}
