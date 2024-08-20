package com.wybase.trans.serve.model.vo;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 定时任务vo
 * @author weiyu
 * @date 2024/8/19
 */
@Data
@ToString
public class QuartzJobVo {
    /**
     * 任务id
     */
    private String jobId;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务状态：0-开启，1-关闭
     */
    private String jobStat;

    /**
     * 任务是否允许并发：0-允许，1-不允许
     */
    private String concurrent;

    /**
     * 任务执行失败策略：1-放弃执行，2-执行一次，3-全部执行
     */
    private String misfirePolicy;

    /**
     * 任务配置方式：0-simpl，1-cron表达式
     */
    private String cronType;

    /**
     * cron表达式
     */
    private String cronExpression;

    /**
     * 执行次数
     */
    private Integer expressionCount;

    /**
     * 执行间隔时间（秒）
     */
    private Integer expressionInterval;

    /**
     * 任务开始时间
     */
    private LocalDateTime startTime;

    /**
     * 任务结束时间
     */
    private LocalDateTime endTime;

    /**
     * 任务类型
     */
    private String jobType;

    /**
     * 任务关联的id集合
     */
    private List<String> jobRelaIds;

    /**
     * 备注
     */
    private String remark;
}
