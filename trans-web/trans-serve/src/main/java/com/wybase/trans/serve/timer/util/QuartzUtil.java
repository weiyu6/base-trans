package com.wybase.trans.serve.timer.util;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.wybase.trans.base.exception.TransException;
import com.wybase.trans.base.result.ResultCodeEnum;
import com.wybase.trans.serve.model.entity.generate.CronJobConfig;
import com.wybase.trans.serve.timer.consts.QuartzConsts;
import com.wybase.trans.serve.timer.domain.QuartzJobConcurrentExecution;
import com.wybase.trans.serve.timer.domain.QuartzJobExecution;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZoneId;
import java.util.Date;

/**
 * Quartz工具类，用于操作Quartz调度器
 * @author weiyu
 * @date 2024/8/19
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuartzUtil {
    private static final Logger logger = LoggerFactory.getLogger(QuartzUtil.class);

    /**
     * 创建定时任务
     * @param scheduler 调度器实例
     * @param cronJobConfig 定时任务配置信息，包括任务ID、任务类型、是否允许并发、定时表达式等
     * @throws TransException 当创建任务失败时抛出业务异常
     */
    public static void createJob(Scheduler scheduler, CronJobConfig cronJobConfig) {
        if (ObjectUtil.isEmpty(cronJobConfig)) {
            logger.error("创建定时任务失败，任务配置为空");
            throw new TransException(ResultCodeEnum.JOB_CONFIG_NULL);
        }
        try {
            // 根据配置获取任务类
            Class<? extends Job> jobClass = getJobClass(cronJobConfig);
            // 构造任务的名称，包含任务ID、任务类型和并发配置信息
            String jobName = getJobName(cronJobConfig);
            // 创建任务详情实例
            JobDetail jobDetail = getJobDetail(cronJobConfig, jobClass, jobName);
            // 创建触发器实例
            Trigger trigger = getTrigger(cronJobConfig, jobName);
            // 将任务和触发器添加到调度器中
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            logger.error("创建定时任务失败", e);
            throw new TransException(ResultCodeEnum.QUARTZ_CREATE_FAIL);
        }
    }

    /**
     * 更新定时任务
     * @param scheduler 调度器实例
     * @param cronJobConfig 定时任务配置信息
     */
    public static void updateJob(Scheduler scheduler, CronJobConfig cronJobConfig) {
        try {
            // 根据配置生成任务键
            JobKey jobKey = JobKey.jobKey(getJobName(cronJobConfig));
            // 检查任务是否已经存在
            if (scheduler.checkExists(jobKey)) {
                // 如果任务存在，则删除任务
                scheduler.deleteJob(jobKey);
            }
            // 创建新的任务
            createJob(scheduler, cronJobConfig);
        } catch (SchedulerException e) {
            logger.error("更新定时任务失败", e);
            throw new TransException(ResultCodeEnum.QUARTZ_UPDATE_FAIL);
        }
    }

    /**
     * 删除定时任务
     * @param scheduler 调度器实例
     * @param cronJobConfig 定时任务配置信息
     */
    public static void deleteJob(Scheduler scheduler, CronJobConfig cronJobConfig) {
        try {
            // 根据配置生成任务键
            JobKey jobKey = JobKey.jobKey(getJobName(cronJobConfig));
            // 检查任务是否存在
            if (scheduler.checkExists(jobKey)) {
                // 如果任务存在，则删除任务
                scheduler.deleteJob(jobKey);
            }
        } catch (SchedulerException e) {
            logger.error("删除定时任务失败", e);
            throw new TransException(ResultCodeEnum.QUARTZ_DELETE_FAIL);
        }
    }

    /**
     * 暂停定时任务
     * @param scheduler 调度器实例
     * @param cronJobConfig 定时任务配置信息
     */
    public static void pauseJob(Scheduler scheduler, CronJobConfig cronJobConfig) {
        try {
            // 根据配置生成任务键
            JobKey jobKey = JobKey.jobKey(getJobName(cronJobConfig));
            // 检查任务是否存在
            if (scheduler.checkExists(jobKey)) {
                // 如果任务存在，则暂停任务
                scheduler.pauseJob(jobKey);
            }
        } catch (SchedulerException e) {
            logger.error("暂停定时任务失败", e);
            throw new TransException(ResultCodeEnum.QUARTZ_PAUSE_FAIL);
        }
    }

    /**
     * 恢复定时任务
     * @param scheduler 调度器实例
     * @param cronJobConfig 定时任务配置信息
     */
    public static void resumeJob(Scheduler scheduler, CronJobConfig cronJobConfig) {
        try {
            // 根据配置生成任务键
            JobKey jobKey = JobKey.jobKey(getJobName(cronJobConfig));
            // 检查任务是否存在
            if (scheduler.checkExists(jobKey)) {
                // 如果任务存在，则恢复任务
                scheduler.resumeJob(jobKey);
            }
        } catch (SchedulerException e) {
            logger.error("恢复定时任务失败", e);
            throw new TransException(ResultCodeEnum.QUARTZ_RESUME_FAIL);
        }
    }

    /**
     * 获取任务详情
     * @param cronJobConfig 定时任务配置信息
     * @param jobClass 任务类
     * @param jobName 任务名称
     * @return 任务详情
     */
    private static JobDetail getJobDetail(CronJobConfig cronJobConfig, Class<? extends Job> jobClass, String jobName) {
        // 根据任务类和任务名称创建一个任务实例
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobName)
                .withDescription(cronJobConfig.getRemark())
                .build();
        // 将任务配置信息序列化后存入任务的DataMap中，以便在任务执行时使用
        jobDetail.getJobDataMap().put(QuartzConsts.QUARTZ_JOB_DETAILS, JSON.toJSONString(cronJobConfig));
        return jobDetail;
    }

    /**
     * 获取触发器
     * @param cronJobConfig 定时任务配置信息
     * @param jobName 任务名称
     * @return 触发器
     */
    private static Trigger getTrigger(CronJobConfig cronJobConfig, String jobName) {
        // 根据传入的jobName生成Trigger的唯一标识
        TriggerBuilder<Trigger> triggerTriggerBuilder = TriggerBuilder.newTrigger()
                .withIdentity(jobName);
        if (cronJobConfig.getStartTime() != null && cronJobConfig.getEndTime() != null) {
            triggerTriggerBuilder
                    .startAt(Date.from(cronJobConfig.getStartTime().atZone(ZoneId.systemDefault()).toInstant()))
                    .endAt(Date.from(cronJobConfig.getEndTime().atZone(ZoneId.systemDefault()).toInstant()));
        }
        // 根据不同的Cron类型设置Trigger的调度方式
        if (StringUtils.equals(QuartzConsts.CRON_TYPE_0, cronJobConfig.getCronType())) {
            // 对于简单调度类型，设置Trigger的开始时间、结束时间和重复次数
            triggerTriggerBuilder
                    .withSchedule(SimpleScheduleBuilder
                            .simpleSchedule()
                            .withMisfireHandlingInstructionNowWithExistingCount()
                            .withIntervalInSeconds(cronJobConfig.getExpressionInterval())
                            .withRepeatCount(cronJobConfig.getExpressionCount()));
        } else if (StringUtils.equals(QuartzConsts.CRON_TYPE_1, cronJobConfig.getCronType())) {
            // 对于复杂Cron表达式类型，设置Trigger的调度规则
            triggerTriggerBuilder
                    .withSchedule(CronScheduleBuilder
                            .cronSchedule(cronJobConfig.getCronExpression())
                            .withMisfireHandlingInstructionDoNothing());
        }

        // 创建触发器，与任务绑定
        return triggerTriggerBuilder.build();
    }

    /**
     * 获取任务执行类
     * @param cronJobConfig 定时任务配置信息
     * @return 任务执行类
     */
    private static Class<? extends Job> getJobClass(CronJobConfig cronJobConfig) {
        // 获取任务是否允许并发执行的配置
        String concurrent = cronJobConfig.getConcurrent();
        // 根据并发配置选择合适的任务执行类
        Class<? extends Job> jobClass = QuartzJobExecution.class;
        // 如果任务不允许并发执行，则使用禁止并发的任务执行类
        if (StringUtils.equals(QuartzConsts.CONCURRENT_1, concurrent)) {
            jobClass = QuartzJobConcurrentExecution.class;
        }
        return jobClass;
    }

    /**
     * 获取任务名称
     * @param cronJobConfig 定时任务配置信息
     * @return 任务名称
     */
    public static String getJobName(CronJobConfig cronJobConfig) {
        return cronJobConfig.getJobId() + "-" + cronJobConfig.getJobType() + "-" + cronJobConfig.getConcurrent();
    }

}

