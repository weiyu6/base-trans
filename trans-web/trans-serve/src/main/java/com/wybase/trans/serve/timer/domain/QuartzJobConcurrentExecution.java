package com.wybase.trans.serve.timer.domain;

import com.wybase.trans.serve.timer.util.JobExecutionUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;

/**
 * 定时任务执行（禁止并发执行）
 * @author weiyu
 * @date 2024/8/19
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class QuartzJobConcurrentExecution extends QuartzJobAbstract {

    /**
     * 执行任务的方法
     * 该方法使用 JobExecutionUtils 工具类来执行任务，通过传入的 JobExecutionContext 上下文对象来获取任务执行的必要信息
     * @param jobExecutionContext JobExecutionContext 类型的参数，包含任务执行的上下文信息
     */
    @Override
    protected void doExcute(JobExecutionContext jobExecutionContext) {
        JobExecutionUtils.execteMethod(jobExecutionContext);
    }
}

