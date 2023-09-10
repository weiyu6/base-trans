package com.wybase.trans.serve.controller;

import com.alibaba.fastjson2.JSONObject;
import com.wybase.trans.base.exception.TransException;
import com.wybase.trans.base.result.Result;
import com.wybase.trans.base.result.ResultCodeEnum;
import com.wybase.trans.serve.dto.LoginInput;
import com.wybase.trans.serve.dto.LoginOutput;
import com.wybase.trans.serve.service.ILoginService;
import com.wybase.trans.serve.vo.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "登录服务", tags = "登录服务")
@RestController
@RequestMapping("/online/auth")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private ILoginService loginService;

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo) {
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
        logger.debug("LoginController.login end:<<<<<<<<<<<<<<<<<");
        return Result.ok(object);
    }

    @ApiOperation(value = "获取用户信息")
    @PostMapping("/info")
    public Result info(@RequestBody LoginVo loginVo) {
        logger.debug("LoginController.info begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("request:{}", loginVo);
        //String tokenHear = request.getHeader("tokenHear");
        String token = loginVo.getToken();
        LoginInput input = new LoginInput();
        input.setToken(token);
        LoginOutput loginRes = loginService.info(input);
        logger.debug("LoginController.info end:<<<<<<<<<<<<<<<<<");
        return Result.ok(loginRes);
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public Result logout(@RequestBody LoginVo request) {
        logger.debug("LoginController.logout begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("request:{}", request);
        String token = request.getToken();
        loginService.logout(token);
        logger.debug("LoginController.logout end:<<<<<<<<<<<<<<<<<");
        return Result.ok();
    }
}
