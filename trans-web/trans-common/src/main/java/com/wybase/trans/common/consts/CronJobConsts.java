package com.wybase.trans.common.consts;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 定时任务常量
 * @author weiyu
 * @date 2024/8/21
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CronJobConsts {
    /**
     * 任务配置方式：0-simpl，1-cron表达式
     */
    public static final String CRON_TYPE_SIMPL = "0";
    /**
     * cron表达式类型：0-simpl，1-cron表达式
     */
    public static final String CRON_TYPE_CRON = "1";

    /**
     * 任务状态：0-开启，1-关闭
     */
    public static final String JOB_STAT_ENABLE = "0";
    /**
     * 任务状态：0-开启，1-关闭
     */
    public static final String JOB_STAT_CLOSE = "1";
    /**
     * 参数
     */
    public static final String QUARTZ_JOB_DETAILS = "JobDetails";
    /**
     * 任务是否允许并发：0-允许，1-不允许
     */
    public static final String CONCURRENT_0 = "0";
    /**
     * 任务是否允许并发：0-允许，1-不允许
     */
    public static final String CONCURRENT_1 = "1";
}
