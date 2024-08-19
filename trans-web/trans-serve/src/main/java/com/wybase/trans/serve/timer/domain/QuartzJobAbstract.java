package com.wybase.trans.serve.timer.domain;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author weiyu
 * @date 2024/8/19
 */
public abstract class QuartzJobAbstract implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        doExcute(context);
    }

    protected abstract void doExcute(JobExecutionContext jobExecutionContext);


}
