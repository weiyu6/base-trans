package com.wybase.trans.serve.config;

import static com.alibaba.druid.filter.config.ConfigTools.encrypt;
import static com.alibaba.druid.filter.config.ConfigTools.genKeyPair;

/**
 * 数据库密码加密
 * @author weiyu
 * @date 2024/6/24
 */
public class PwdTools {
    public static void main(String[] args) {
        try {
            String password = "root";
            String[] arr = genKeyPair(512);
            System.out.println("privateKey:" + arr[0]);
            System.out.println("publicKey:" + arr[1]);
            System.out.println("password:" + encrypt(arr[0], password));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
