package com.wybase.trans.serve.service;

import com.mybatisflex.core.service.IService;
import com.wybase.trans.serve.model.entity.generate.CronJobConfig;
import com.wybase.trans.serve.model.vo.QuartzJobVo;

/**
 * 定时任务配置表 服务层。
 *
 * @author weiyu
 * @since 2024-08-19
 */
public interface ICronJobConfigService extends IService<CronJobConfig> {

    /**
     * 新增定时任务配置表。
     */
    void addJob(QuartzJobVo quartzJobVo);
}
