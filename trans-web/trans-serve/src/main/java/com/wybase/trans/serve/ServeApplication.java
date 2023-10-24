package com.wybase.trans.serve;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 数字工具类
 * @author weiyu
 * @date 2023/07/28
 */
@ComponentScan("com.wybase")
@MapperScan({"com.wybase.trans.serve.mapper"})
@SpringBootApplication
public class ServeApplication {
    private static final Logger logger = LoggerFactory.getLogger(ServeApplication.class);
    public static void main(String[] args){
        SpringApplication.run(ServeApplication.class, args);
        logger.info("=========服务启动成功！=========");
    }
}