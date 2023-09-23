package com.wybase.trans.serve.service;

import com.alibaba.fastjson2.JSONObject;
import com.wybase.trans.serve.model.dto.LoginInput;
import com.wybase.trans.serve.model.dto.LoginOutput;

/**
 * 登录注册服务
 * @author weiyu
 * @date 2023/8/5
 */
public interface ILoginService {

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    JSONObject login(String username, String password);

    /**
     * 获取用户信息
     * @param input 输入参数
     * @return
     */
    LoginOutput info(LoginInput input);

    /**
     * 退出登录
     * @param token
     */
    void logout(String token);
}
