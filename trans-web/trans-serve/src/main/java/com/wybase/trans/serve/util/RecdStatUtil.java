package com.wybase.trans.serve.util;

import com.wybase.trans.base.exception.TransException;
import com.wybase.trans.base.result.ResultCodeEnum;
import com.wybase.trans.common.consts.TransConsts;
import org.apache.commons.lang3.StringUtils;

/**
 * 记录状态检查工具类
 * @author weiyu
 * @date 2023/10/14
 */
public class RecdStatUtil {
    /**
     * 检查记录状态是否正常
     * @param recdStat
     */
    public static void recdStatChk(String recdStat){
        if(!StringUtils.equals(recdStat, TransConsts.RECD_STAT_0)){
            throw new TransException(ResultCodeEnum.RECDSTAT_ERROR);
        }
    }
}
