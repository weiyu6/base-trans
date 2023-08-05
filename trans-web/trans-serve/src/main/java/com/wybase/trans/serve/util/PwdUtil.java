package com.wybase.trans.serve.util;

import com.wybase.trans.common.util.EncryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 密码工具类
 * @author weiyu
 * @date 2023/8/5
 */
@Component
public class PwdUtil {
    private static final Logger logger = LoggerFactory.getLogger(PwdUtil.class);
    @Value("${util.properties.pwdKey}")
    private String SECRET_KEY;

    /**
     * 密码加密，固定秘钥加用户id作为秘钥生成密码
     * @param userId 用户id
     * @param pwd 密码
     * @return
     */
    public String encryptnPwd(String userId, String pwd) {
        logger.debug("PwdService.encryptnPwd begin >>>>>>>>>>>>>>>>>>>");
        String secretKey = SECRET_KEY + userId;
        String encryptPwd = EncryptUtil.encrypt(pwd, secretKey);
        logger.debug("加密后的密码：{}", encryptPwd);
        logger.debug("PwdService.encryptnPwd end:<<<<<<<<<<<<<<<<<");
        return encryptPwd;
    }

    /**
     * 密码校验
     * @param userId 用户id
     * @param newPwd 输入的密码
     * @param oldPwd 账户密码
     * @return
     */
    public boolean pwdChk(String userId, String newPwd, String oldPwd) {
        logger.debug("PwdService.pwdChk begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("userId:{},newPwd:{},oldPwd:{}", userId, newPwd, oldPwd);
        String pwd = encryptnPwd(userId, newPwd);
        logger.debug("PwdService.pwdChk end:<<<<<<<<<<<<<<<<<");
        return StringUtils.equals(pwd, oldPwd);
    }
}
