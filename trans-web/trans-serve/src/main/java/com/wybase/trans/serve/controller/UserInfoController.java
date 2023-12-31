package com.wybase.trans.serve.controller;

import com.wybase.trans.base.aspect.MethodName;
import com.wybase.trans.base.exception.TransException;
import com.wybase.trans.base.result.Result;
import com.wybase.trans.base.result.ResultCodeEnum;
import com.wybase.trans.common.consts.TransConsts;
import com.wybase.trans.serve.model.dto.UserInfoInput;
import com.wybase.trans.serve.model.dto.UserInfoOutput;
import com.wybase.trans.serve.model.entity.custom.UserInfoExtend;
import com.wybase.trans.serve.model.vo.UserInfoVo;
import com.wybase.trans.serve.service.IUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户操作
 * @author weiyu
 * @date 2023/10/15
 */
@Api(value = "用户服务", tags = "用户服务")
@RestController
@RequestMapping("/online/user")
public class UserInfoController {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private IUserInfoService userInfoService;

    @MethodName(value = "查询用户列表", transType = TransConsts.TRANS_TYPE_1)
    @ApiOperation(value = "查询用户列表")
    @PostMapping("/userInfoList")
    public Result userInfoList(@RequestBody UserInfoVo userInfoVo) {
        logger.debug("UserController.userInfoList begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("userInfoVo:{}", userInfoVo);
        UserInfoInput input = new UserInfoInput();
        BeanUtils.copyProperties(userInfoVo, input);
        UserInfoOutput serviceOutput = userInfoService.userInfoListPage(input);
        logger.debug("UserController.userInfoList end:<<<<<<<<<<<<<<<<<");
        return Result.ok(serviceOutput);
    }

    @MethodName(value = "根据用户id查询用户信息", transType = TransConsts.TRANS_TYPE_1)
    @ApiOperation(value = "根据用户id查询用户信息")
    @PostMapping("/userInfoById")
    public Result userInfoById(@RequestBody UserInfoVo userInfoVo) {
        logger.debug("UserController.userQryById begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("userInfoVo:{}", userInfoVo);
        String userId = userInfoVo.getUserId();
        if (StringUtils.isEmpty(userId)) {
            throw new TransException(ResultCodeEnum.NULL_ERROR, "用户id不能为空");
        }
        UserInfoExtend userExtend = userInfoService.userExtendById(userId);
        logger.debug("UserController.userQryById end:<<<<<<<<<<<<<<<<<");
        return Result.ok(userExtend);
    }

    @MethodName(value = "修改用户信息", transType = TransConsts.TRANS_TYPE_0)
    @ApiOperation(value = "修改用户信息")
    @PostMapping("/userInfoMdf")
    public Result userInfoMdf(@RequestBody UserInfoVo userInfoVo) {
        logger.debug("UserController.userInfoMdf begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("userInfoVo:{}", userInfoVo);
        String userId = userInfoVo.getUserId();
        if (StringUtils.isEmpty(userId)) {
            throw new TransException(ResultCodeEnum.NULL_ERROR, "用户id不能为空");
        }
        UserInfoInput userInput = new UserInfoInput();
        BeanUtils.copyProperties(userInfoVo, userInput);
        userInfoService.userInfoMdf(userInput);
        logger.debug("UserController.userInfoMdf end:<<<<<<<<<<<<<<<<<");
        return Result.ok();
    }

    @MethodName(value = "用户注册", transType = TransConsts.TRANS_TYPE_0)
    @ApiOperation(value = "用户注册")
    @PostMapping("/addUser")
    public Result addUser(@RequestBody UserInfoVo userInfoVo) {
        logger.debug("addUser begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("userInfoVo:{}", userInfoVo);
        String userNm = userInfoVo.getUserNm();
        String passWord = userInfoVo.getPassWord();
        if (StringUtils.isEmpty(userNm)) {
            logger.error("用户名不能为空");
            throw new TransException(ResultCodeEnum.NULL_ERROR, "用户名不能为空");
        }
        if (StringUtils.isEmpty(passWord)) {
            logger.error("密码不能为空");
            throw new TransException(ResultCodeEnum.NULL_ERROR, "密码不能为空");
        }
        UserInfoInput userInput = new UserInfoInput();
        BeanUtils.copyProperties(userInfoVo, userInput);
        // 调用用户注册服务
        userInfoService.addUser(userInput);
        // TODO 调用短信服务发送通知
        logger.debug("addUser end:<<<<<<<<<<<<<<<<<");
        return Result.ok();
    }
}
