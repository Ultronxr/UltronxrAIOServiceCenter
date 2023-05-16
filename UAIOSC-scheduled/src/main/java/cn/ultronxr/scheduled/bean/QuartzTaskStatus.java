package cn.ultronxr.scheduled.bean;

import org.jetbrains.annotations.NotNull;

/**
 * @author Ultronxr
 * @date 2023/04/15 19:08:05
 * @description QuartzTask任务状态枚举封装
 */
public enum QuartzTaskStatus {

    /** 任务进行状态：停止的；对应数据库 quartz_task 字段 status=0 */
    STOPPED(0, "停止的"),
    /** 任务进行状态：激活的；对应数据库 quartz_task 字段 status=1 */
    ACTIVATED(1, "激活的"),
    /** 任务进行状态：暂停的；对应数据库 quartz_task 字段 status=2 */
    PAUSED(2, "暂停的");


    private final Integer status;
    private final String info;

    QuartzTaskStatus(Integer status, String info) {
        this.status = status;
        this.info = info;
    }

    public Integer getStatus() {
        return this.status;
    }

    public String getInfo() {
        return this.info;
    }

    public static @NotNull Boolean isStopped(Integer targetStatus) {
        return STOPPED.getStatus().equals(targetStatus);
    }

    public static @NotNull Boolean isActivated(Integer targetStatus) {
        return ACTIVATED.getStatus().equals(targetStatus);
    }

    public static @NotNull Boolean isPaused(Integer targetStatus) {
        return PAUSED.getStatus().equals(targetStatus);
    }

}
