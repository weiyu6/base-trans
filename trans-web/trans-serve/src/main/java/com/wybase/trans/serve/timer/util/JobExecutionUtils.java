package com.wybase.trans.serve.timer.util;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.wybase.trans.base.exception.TransException;
import com.wybase.trans.base.result.ResultCodeEnum;
import com.wybase.trans.serve.config.TransApplicationContext;
import com.wybase.trans.serve.model.entity.generate.CronJobConfig;
import com.wybase.trans.serve.timer.consts.QuartzConsts;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 定时任务执行工具类
 * @author weiyu
 * @date 2024/8/19
 */
public class JobExecutionUtils {
    private static final Logger logger = LoggerFactory.getLogger(JobExecutionUtils.class);

    /**
     * 执行定时任务
     */
    public static void execteMethod(JobExecutionContext context) {
        // 从上下文中获取定时任务详情
        JobDetail jobDetail = context.getJobDetail();
        // 检查定时任务信息是否为空
        if (ObjectUtil.isEmpty(jobDetail)) {
            logger.error("定时任务信息为空");
            throw new TransException(ResultCodeEnum.ERROR, "定时任务信息为空");
        }
        Object object = jobDetail.getJobDataMap().get(QuartzConsts.QUARTZ_JOB_DETAILS);
        if (ObjectUtil.isEmpty(object)) {
            logger.error("定时任务信息为空");
            throw new TransException(ResultCodeEnum.ERROR, "定时任务信息为空");
        }
        try {
            CronJobConfig cronJobConfig = JSON.parseObject(object.toString(), CronJobConfig.class);
            // 将定时任务信息转换为JobDetails对象
            // 通过上下文获取任务对应的bean
            Object bean = TransApplicationContext.getBean(cronJobConfig.getBeanTarget());
            // 获取bean对应的方法
            Method method = bean.getClass().getMethod(cronJobConfig.getBeanMethodTarget(), CronJobConfig.class);
            // 调用方法
            method.invoke(bean, cronJobConfig);
        } catch (Exception e) {
            // 捕获异常，记录并抛出
            logger.error("定时任务信息映射失败！", e);
            throw new TransException(ResultCodeEnum.ERROR, "定时任务信息映射失败！");
        }
    }

}
