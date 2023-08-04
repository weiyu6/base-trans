package com.wybase.trans.serve.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Knife4j 配置类
 * @author weiyu
 * @date 2023/8/4
 */
@Configuration
@EnableKnife4j
@EnableSwagger2
public class Knife4jConfig {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("基础架构")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wybase.trans.serve"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("遇见博客")
                .description("更多博客文章请关注：https://www.meetblog.com/")
                .termsOfServiceUrl("https://www.meetblog.com/")
                .contact(new Contact("weiyu", "", "weiyulearn@163.com"))
                .version("1.0")
                .build();
    }
}
