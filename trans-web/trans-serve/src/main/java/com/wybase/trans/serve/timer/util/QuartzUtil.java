package com.wybase.trans.serve.timer.util;

import com.alibaba.fastjson2.JSON;
import com.wybase.trans.base.exception.TransException;
import com.wybase.trans.base.result.ResultCodeEnum;
import com.wybase.trans.serve.model.entity.generate.CronJobConfig;
import com.wybase.trans.serve.timer.consts.QuartzConsts;
import com.wybase.trans.serve.timer.domain.QuartzJobConcurrentExecution;
import com.wybase.trans.serve.timer.domain.QuartzJobExecution;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Quartz工具类，用于操作Quartz调度器
 * @author weiyu
 * @date 2024/8/19
 */
public class QuartzUtil {
    private static final Logger logger = LoggerFactory.getLogger(QuartzUtil.class);

    /**
     * 创建定时任务
     * @param scheduler 调度器实例
     * @param cronJobConfig 定时任务配置信息，包括任务ID、任务类型、是否允许并发、定时表达式等
     * @throws TransException 当创建任务失败时抛出业务异常
     */
    public static void createJob(Scheduler scheduler, CronJobConfig cronJobConfig) {

        try {
            // 获取任务是否允许并发执行的配置
            String concurrent = cronJobConfig.getConcurrent();
            // 根据并发配置选择合适的任务执行类
            Class <? extends Job> jobClass = QuartzJobExecution.class;
            // 如果任务不允许并发执行，则使用禁止并发的任务执行类
            if (StringUtils.equals(QuartzConsts.CONCURRENT_1, concurrent)) {
                jobClass = QuartzJobConcurrentExecution.class;
            }
            // 构造任务的名称，包含任务ID、任务类型和并发配置信息
            String jobName = cronJobConfig.getJobId() + "-" + cronJobConfig.getJobType() + "-" + cronJobConfig.getConcurrent();
            // 创建一个任务实例
            JobDetail jobDetail = JobBuilder.newJob(jobClass)
                    .withIdentity(jobName)
                    .withDescription(cronJobConfig.getRemark())
                    .build();
            jobDetail.getJobDataMap().put(QuartzConsts.QUARTZ_JOB_DETAILS,JSON.toJSONString(cronJobConfig));
            // 创建一个基于Cron表达式的调度器
            ScheduleBuilder schedBuilder = CronScheduleBuilder.cronSchedule(cronJobConfig.getCronExpression());
            // 如果是简单调度类型，则使用简单调度构建器
            if (StringUtils.equals(QuartzConsts.CRON_TYPE_0, cronJobConfig.getCronType())) {
                schedBuilder = SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(cronJobConfig.getExpressionInterval())
                        .withRepeatCount(cronJobConfig.getExpressionCount());
            }
            // 创建触发器，与任务绑定
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobName)
                    .withSchedule(schedBuilder)
                    .build();

            // 将任务和触发器添加到调度器中
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            // 记录任务创建失败的日志并抛出异常
            logger.error("创建定时任务失败", e);
            throw new TransException(ResultCodeEnum.QUZRTZ_CREATE_ERROR);
        }

    }

}

