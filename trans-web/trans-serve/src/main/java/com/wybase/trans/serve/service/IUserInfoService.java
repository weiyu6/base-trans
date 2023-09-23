package com.wybase.trans.serve.service;

import com.mybatisflex.core.service.IService;
import com.wybase.trans.serve.model.entity.generate.UserInfo;

/**
 * 用户信息表 服务层。
 *
 * @author weiyu
 * @since 2023-08-05
 */
public interface IUserInfoService extends IService<UserInfo> {

    /**
     * 查询用户总数
     * @return
     */
    long userCount();
}
