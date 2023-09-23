package com.wybase.trans.serve.model.dto;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 登录服务返回接口
 * @author weiyu
 * @date 2022/3/17
 */
@Data
@ToString
@NoArgsConstructor
public class LoginOutput{
    /**
     * 用户昵称
     */
    private String name;
    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户权限
     */
    private List<String> roles;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户Token
     */
    private String token;

    /**
     * 密码检查结果：0-正确，1-错误
     */
    private String pwdChkFlg;

    /**
     * 菜单列表
     */
    private List<JSONObject> menuList;
}
