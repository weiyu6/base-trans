package com.wybase.trans.serve;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 数字工具类
 * @author weiyu
 * @date 2023/07/28
 */
@SpringBootApplication
public class ApplicationServe {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationServe.class);
    public static void main(String[] args){
        SpringApplication.run(ApplicationServe.class, args);
        logger.info("=========服务启动成功！=========");
    }
}