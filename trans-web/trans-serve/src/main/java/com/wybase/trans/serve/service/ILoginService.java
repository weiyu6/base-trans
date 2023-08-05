package com.wybase.trans.serve.service;

import com.alibaba.fastjson2.JSONObject;

/**
 * 登录注册服务
 * @author weiyu
 * @date 2023/8/5
 */
public interface ILoginService {

    JSONObject login(String username, String password);
}
