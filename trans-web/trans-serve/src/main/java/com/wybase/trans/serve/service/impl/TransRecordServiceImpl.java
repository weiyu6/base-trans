package com.wybase.trans.serve.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wybase.trans.serve.entity.generate.TransRecordEntity;
import com.wybase.trans.serve.mapper.generate.TransRecordMapper;
import com.wybase.trans.serve.service.ITransRecordService;
import org.springframework.stereotype.Service;

/**
 * 交易流水表 服务层实现。
 *
 * @author weiyu
 * @since 2023-08-01
 */
@Service
public class TransRecordServiceImpl extends ServiceImpl<TransRecordMapper, TransRecordEntity> implements ITransRecordService {

}
