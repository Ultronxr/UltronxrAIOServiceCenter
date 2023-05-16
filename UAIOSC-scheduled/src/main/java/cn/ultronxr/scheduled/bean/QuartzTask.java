package cn.ultronxr.scheduled.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author Ultronxr
 * @date 2023/04/15 18:47:36
 * @description 自定义的 QuartzTask bean ，用于持久化任务信息到数据库，以及提供任务信息给 Quartz 创建 Job
 */
@Data
public class QuartzTask {

    private String taskName;

    private String taskGroup;

    private String taskDescription;

    private String taskClass;

    private String taskCron;

    private Integer status;

    private Date pauseTimeLimit;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

}
