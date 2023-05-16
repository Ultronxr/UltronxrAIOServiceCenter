package cn.ultronxr.scheduled.service;

import cn.ultronxr.scheduled.bean.QuartzTask;

/**
 * @author Ultronxr
 * @date 2023/04/15 18:46:27
 * @description
 */
public interface QuartzService {

    /**
     * 初始化所有QuartzTask任务，并启动 scheduler。初始化任务流程如下：<br/>
     *
     * 从数据库表 quartz_task 中获取所有任务信息，
     * 对每一个 激活状态（{@code status=1}） 的任务调用 {@code addQuartzTask(QuartzTask)} 方法，
     * 将其加入调度器 scheduler
     */
    void initJobsAndStartScheduler();

    /**
     * 尝试向 scheduler 中添加 QuartzTask任务
     * @param task 待加入scheduler的 QuartzTask任务
     * @return 操作结果：true-成功、false-失败
     */
    boolean addJob(QuartzTask task);

    /**
     * 尝试从 scheduler 中停止（删除） QuartzTask任务
     * @param task QuartzTask任务
     * @return 操作结果：true-成功、false-失败
     */
    boolean stopJob(QuartzTask task);

    /**
     * 尝试从 scheduler 中暂停 QuartzTask任务
     * @param task QuartzTask任务
     * @return 操作结果：true-成功、false-失败
     */
    boolean pauseJob(QuartzTask task);

    /**
     * 尝试从 scheduler 中恢复已暂停的 QuartzTask任务
     * @param task QuartzTask任务
     * @return 操作结果：true-成功、false-失败
     */
    Boolean resumeJob(QuartzTask task);

}
