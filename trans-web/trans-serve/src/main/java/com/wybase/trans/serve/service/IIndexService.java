package com.wybase.trans.serve.service;

import com.wybase.trans.serve.model.dto.IndexOutput;

/**
 * @author weiyu
 * @date 2023/8/28
 */
public interface IIndexService {
    /**
     * 首页初始化数据查询
     * @return
     */
    IndexOutput init();
}
