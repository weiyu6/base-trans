package com.wybase.trans.gateway;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 网关服务
 * @author weiyu
 * @date 2023/10/24
 */
@ComponentScan("com.wybase")
@MapperScan({"com.wybase.trans.gateway.mapper"})
@SpringBootApplication
public class GatewayApplication {
    private static final Logger logger = LoggerFactory.getLogger(GatewayApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
        logger.info("=========服务启动成功！=========");
    }
}
