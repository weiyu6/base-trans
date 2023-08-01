package com.wybase.trans.serve.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wybase.trans.serve.entity.generate.SysLogEntity;
import com.wybase.trans.serve.mapper.generate.SysLogMapper;
import com.wybase.trans.serve.service.ISysLogService;
import org.springframework.stereotype.Service;

/**
 * 日志表 服务层实现。
 *
 * @author weiyu
 * @since 2023-08-01
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLogEntity> implements ISysLogService {

}
