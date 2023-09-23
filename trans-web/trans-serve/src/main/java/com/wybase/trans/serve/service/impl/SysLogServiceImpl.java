package com.wybase.trans.serve.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wybase.trans.serve.model.entity.generate.SysLog;
import com.wybase.trans.serve.mapper.generate.SysLogMapper;
import com.wybase.trans.serve.service.ISysLogService;
import org.springframework.stereotype.Service;

/**
 * 日志表 服务层实现。
 *
 * @author weiyu
 * @since 2023-08-05
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

}
