package com.wybase.trans.serve.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wybase.trans.serve.entity.generate.TblTransRecordEntity;
import com.wybase.trans.serve.mapper.generate.TblTransRecordMapper;
import com.wybase.trans.serve.service.TransRecordService;
import org.springframework.stereotype.Service;

/**
 * 交易流水表 服务层实现。
 *
 * @author weiyu
 * @since 2023-07-29
 */
@Service
public class TransRecordServiceImpl extends ServiceImpl<TblTransRecordMapper, TblTransRecordEntity> implements TransRecordService {

}
