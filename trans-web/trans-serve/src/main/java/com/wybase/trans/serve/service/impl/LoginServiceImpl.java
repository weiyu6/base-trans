package com.wybase.trans.serve.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSONObject;
import com.wybase.trans.base.exception.TransException;
import com.wybase.trans.base.result.ResultCodeEnum;
import com.wybase.trans.common.consts.TransHeardConsts;
import com.wybase.trans.serve.config.TransContext;
import com.wybase.trans.serve.dao.UserInfoDao;
import com.wybase.trans.serve.entity.generate.UserInfo;
import com.wybase.trans.serve.service.ILoginService;
import com.wybase.trans.serve.service.IUserInfoService;
import com.wybase.trans.serve.util.PwdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author weiyu
 * @date 2023/8/5
 */
@Service
public class LoginServiceImpl implements ILoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private PwdUtil pwdUtil;

    @Override
    public JSONObject login(String username, String password) {
        logger.debug("LoginServiceImpl.login begin >>>>>>>> [username:{}, password:{}]", username, password);
        JSONObject object = new JSONObject();
        UserInfo userInfo = userInfoDao.queryByUserNm(username);

        if (ObjectUtil.isEmpty(userInfo)) {
            logger.error("用户不存在");
            throw new TransException(ResultCodeEnum.LOGIN_USERNM_ERROR);
        }
        String userId = userInfo.getUserId();
        String oldPwd = userInfo.getPassWord();
//        // 密码校验
        boolean pwdChk = pwdUtil.pwdChk(userId, password, oldPwd);
        // boolean pwdChk = MD5Util.pwdChk(password, oldPwd);
        if (!pwdChk) {
            logger.error("密码错误");
            throw new TransException(ResultCodeEnum.LOGIN_PASSWORD_ERROR);
        }
//        String token = JwtUtil.createToken(userId, username);
        String token = IdUtil.simpleUUID();
//        // 将token存放到redis中
//        String key = TransConsts.USER_LOGIN_TOKEN_ID + userId;
//        boolean result = redisUtil.set(key, token, 2, TimeUnit.HOURS);
//        if (!result) {
//            log.error("Redis写入失败！");
//            throw new TransException(ResultCodeEnum.TRAN100703);
//        }
        TransContext.setField(TransHeardConsts.TOKEN_USER_ID, userId);
        TransContext.setField(TransHeardConsts.TOKEN_USER_NAME, username);
        // 更新用户登录状态

        userInfo.setLoginCount(userInfo.getLoginCount() + 1);
        userInfo.setLastLoginIp(TransContext.getString(TransHeardConsts.IP_ADDR));
        userInfo.setLastLoginTime((LocalDateTime) TransContext.getField(TransHeardConsts.START_DATE_TIME));
        logger.debug("user:{}", userInfo);
        userInfoService.updateById(userInfo, true);
        object.put("token", token);
        object.put("userId", userId);
        return object;
    }
}
