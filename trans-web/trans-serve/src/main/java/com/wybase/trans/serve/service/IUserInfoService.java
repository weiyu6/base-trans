package com.wybase.trans.serve.service;

import com.mybatisflex.core.service.IService;
import com.wybase.trans.serve.model.dto.UserInfoInput;
import com.wybase.trans.serve.model.dto.UserInfoOutput;
import com.wybase.trans.serve.model.entity.custom.UserInfoExtend;
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

    /**
     * 分页查询用户列表
     * @param input
     * @return
     */
    UserInfoOutput userInfoListPage(UserInfoInput input);

    /**
     * 根据用户id查询用户信息
     * @param userId
     * @return
     */
    UserInfoExtend userExtendById(String userId);

    /**
     * 修改用户信息
     * @param userInput
     */
    void userInfoMdf(UserInfoInput userInput);
}
