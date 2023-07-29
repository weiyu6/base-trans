package com.wybase.trans.serve.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wybase.trans.serve.entity.generate.TblSysLogEntity;
import com.wybase.trans.serve.mapper.generate.TblSysLogMapper;
import com.wybase.trans.serve.service.SysLogService;
import org.springframework.stereotype.Service;

/**
 * 日志表 服务层实现。
 *
 * @author weiyu
 * @since 2023-07-29
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<TblSysLogMapper, TblSysLogEntity> implements SysLogService {

}
