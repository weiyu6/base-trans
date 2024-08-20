package com.wybase.trans.serve.service.impl;

import cn.hutool.core.util.IdUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wybase.trans.common.consts.TransConsts;
import com.wybase.trans.common.consts.TransHeardConsts;
import com.wybase.trans.common.util.MapstructUtils;
import com.wybase.trans.serve.config.TransContext;
import com.wybase.trans.serve.mapper.generate.CronJobConfigMapper;
import com.wybase.trans.serve.model.entity.generate.CronJobConfig;
import com.wybase.trans.serve.model.vo.QuartzJobVo;
import com.wybase.trans.serve.service.ICronJobConfigService;
import com.wybase.trans.serve.timer.util.QuartzUtil;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
        String userId = TransContext.getString(TransHeardConsts.TOKEN_USER_ID);
        long flakeId = IdUtil.getSnowflakeNextId();
        String jobId = String.format("q%s", flakeId);
        logger.debug("定时任务ID:{}", jobId);
        CronJobConfig cronJobConfig = MapstructUtils.convert(quartzJobVo, CronJobConfig.class);
        String jobType = cronJobConfig.getJobType();
        if(StringUtils.equals(jobType, "1")){
            cronJobConfig.setBeanTarget("task1");
            cronJobConfig.setBeanMethodTarget("handle");
        }else {
            cronJobConfig.setBeanTarget("task2");
            cronJobConfig.setBeanMethodTarget("handle");
        }
        cronJobConfig.setJobId(jobId);
        cronJobConfig.setCreateUser(userId);
        cronJobConfig.setUpdateUser(userId);
        cronJobConfig.setRecdStat(TransConsts.RECD_STAT_0);
        save(cronJobConfig);
        QuartzUtil.createJob(scheduler, cronJobConfig);
    }
}
