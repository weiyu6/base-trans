package com.wybase.trans.serve.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wybase.trans.serve.mapper.generate.CronJobConfigMapper;
import com.wybase.trans.serve.model.entity.generate.CronJobConfig;
import com.wybase.trans.serve.model.vo.QuartzJobVo;
import com.wybase.trans.serve.service.ICronJobConfigService;
import com.wybase.trans.serve.timer.util.QuartzUtil;
import jakarta.annotation.Resource;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 定时任务配置表 服务层实现。
 * @author weiyu
 * @since 2024-08-19
 */
@Service
public class CronJobConfigServiceImpl extends ServiceImpl<CronJobConfigMapper, CronJobConfig> implements ICronJobConfigService {
    private static final Logger logger = LoggerFactory.getLogger(CronJobConfigServiceImpl.class);

    @Resource
    private Scheduler scheduler;

    /**
     * @param quartzJobVo
     */
    @Override
    public void addJob(QuartzJobVo quartzJobVo) {
        CronJobConfig cronJobConfig = new CronJobConfig();
        cronJobConfig.setJobId("1");
        cronJobConfig.setJobName("任务测试");
        cronJobConfig.setBeanTarget("task1");
        cronJobConfig.setBeanMethodTarget("handle");
        cronJobConfig.setJobStat("0");
        cronJobConfig.setConcurrent("0");
        cronJobConfig.setMisfirePolicy("");
        cronJobConfig.setCronType("1");
        cronJobConfig.setCronExpression("0/5 * * * * ?");
        cronJobConfig.setExpressionCount(0);
        cronJobConfig.setExpressionInterval(0);
        cronJobConfig.setJobType("1");
        cronJobConfig.setRemark("cececece");
        cronJobConfig.setCreateUser("1");
        cronJobConfig.setUpdateUser("1");
        cronJobConfig.setCreateTime(LocalDateTime.now());
        cronJobConfig.setUpdateTime(LocalDateTime.now());
        cronJobConfig.setRecdStat("0");
        save(cronJobConfig);
        QuartzUtil.createJob(scheduler, cronJobConfig);

        cronJobConfig.setJobId("2");
        cronJobConfig.setJobName("222任务测试");
        cronJobConfig.setBeanTarget("task2");
        cronJobConfig.setBeanMethodTarget("handle");
        cronJobConfig.setJobStat("0");
        cronJobConfig.setConcurrent("1");
        cronJobConfig.setMisfirePolicy("");
        cronJobConfig.setCronType("0");
        cronJobConfig.setCronExpression("0/5 * * * * ?");
        cronJobConfig.setExpressionCount(10);
        cronJobConfig.setExpressionInterval(10);
        cronJobConfig.setJobType("1");
        cronJobConfig.setRemark("testesttest");
        cronJobConfig.setCreateUser("1");
        cronJobConfig.setUpdateUser("1");
        cronJobConfig.setCreateTime(LocalDateTime.now());
        cronJobConfig.setUpdateTime(LocalDateTime.now());
        cronJobConfig.setRecdStat("0");
        save(cronJobConfig);
        QuartzUtil.createJob(scheduler, cronJobConfig);
    }
}
