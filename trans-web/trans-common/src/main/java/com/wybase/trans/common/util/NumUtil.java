package com.wybase.trans.common.util;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * 数字工具类
 * @author weiyu
 * @date 2023/07/29
 */
public class NumUtil {

    private static final Random random = new Random();

    private static final DecimalFormat fourdf = new DecimalFormat("0000");
    private static final DecimalFormat sixdf = new DecimalFormat("000000");

    /**
     * 随机生成4位整数
     * @return
     */
    public static String getFourBitRandom(){
        return fourdf.format(random.nextInt(10000));
    }

    /**
     * 随机生成6位整数
     * @return
     */
    public static String getSixBitRandom(){
        return sixdf.format(random.nextInt(1000000));
    }

}
