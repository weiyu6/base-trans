package com.wybase.trans.serve.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 登录服务请求接口
 * @author weiyu
 * @date 2022/3/18
 */
@Data
@ToString
@NoArgsConstructor
public class LoginInput {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * 用户token
     */
    private String token;

    /**
     * 用户ID
     */
    private String userId;

}
