package com.wybase.trans.serve.controller;

import com.alibaba.fastjson2.JSONObject;
import com.wybase.trans.base.exception.TransException;
import com.wybase.trans.base.result.Result;
import com.wybase.trans.base.result.ResultCodeEnum;

import com.wybase.trans.serve.service.ILoginService;
import com.wybase.trans.serve.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录校验
 * @author weiyu
 * @date 2023/8/5
 */
@RestController
@RequestMapping("/online/auth")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private ILoginService loginService;


    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo){
        logger.info("LoginController.login begin >>>>>>>>>>>>>>>>>>>");
        logger.info("request:{}", loginVo);
        String username = loginVo.getUsername();
        String password = loginVo.getPassword();
        if (StringUtils.isEmpty(username)) {
            throw new TransException(ResultCodeEnum.NULL_ERROR, "用户名不能为空");
        }
        if (StringUtils.isEmpty(password)) {
            throw new TransException(ResultCodeEnum.NULL_ERROR, "密码不能为空");
        }
        // 校验用户信息，并获取token
        JSONObject object = loginService.login(username, password);
        String token = object.getString("token");
        String userId = object.getString("userId");


//
//        LoginResponse response = new LoginResponse();
//        response.setToken(token);
//        response.setUserId(userId);
//        log.debug("LoginController.login end:<<<<<<<<<<<<<<<<<");
        return Result.ok().data(object);
    }
}
