package com.wybase.trans.serve.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wybase.trans.serve.model.entity.generate.CronTaskConfig;
import com.wybase.trans.serve.mapper.generate.CronTaskConfigMapper;
import com.wybase.trans.serve.service.ICronTaskConfigService;
import org.springframework.stereotype.Service;

/**
 * 定时任务配置表 服务层实现。
 *
 * @author weiyu
 * @since 2024-08-19
 */
@Service
public class CronTaskConfigServiceImpl extends ServiceImpl<CronTaskConfigMapper, CronTaskConfig> implements ICronTaskConfigService {

}
