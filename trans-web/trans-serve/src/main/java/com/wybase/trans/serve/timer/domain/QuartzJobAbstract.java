package com.wybase.trans.serve.timer.domain;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 抽象类QuartzJobAbstract实现了Job接口，提供了一个执行任务的框架
 * 子类需要实现doExcute方法来定义具体的任务执行逻辑
 * @author weiyu
 * @date 2024/8/19
 */
public abstract class QuartzJobAbstract implements Job {

    /**
     * 执行任务的主要方法，由doExcute方法实现具体的执行逻辑
     *
     * @param context 任务执行上下文，包含任务执行所需的信息和工具
     * @throws JobExecutionException 如果任务执行过程中发生异常，将抛出此异常
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        doExcute(context);
    }

    /**
     * 抽象方法，由子类实现，用于定义任务的具体执行逻辑
     *
     * @param jobExecutionContext 任务执行上下文，包含任务执行所需的信息和工具
     */
    protected abstract void doExcute(JobExecutionContext jobExecutionContext);

}

