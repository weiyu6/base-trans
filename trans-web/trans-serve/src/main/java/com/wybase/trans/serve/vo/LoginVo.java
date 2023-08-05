package com.wybase.trans.serve.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 登录请求接口
 * @author weiyu
 * @date 2023/8/5
 */
@Data
@ToString
@NoArgsConstructor
public class LoginVo{
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     *
     */
    private String token;

    private String userId;
}
