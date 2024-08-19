package com.wybase.trans.serve.timer.consts;

/**
 * 定时任务常量
 * @author weiyu
 * @date 2024/8/19
 */
public class QuartzConsts {

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
    /**
     * 任务配置方式：0-simpl，1-cron表达式
     */
    public static final CharSequence CRON_TYPE_0 = "0";

    /**
     * 任务配置方式：0-simpl，1-cron表达式
     */
    public static final CharSequence CRON_TYPE_1 = "1";

    /**
     * 1-放弃执行。
     */
    public static final String MISFIRE_DO_NOTHING = "1";

    /**
     * 2-执行一次。
     */
    public static final String MISFIRE_FIRE_AND_PROCEED = "2";

    /**
     * 3-全部执行。
     */
    public static final String MISFIRE_IGNORE_MISFIRES = "3";
}
