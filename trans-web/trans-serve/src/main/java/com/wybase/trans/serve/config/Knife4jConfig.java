package com.wybase.trans.serve.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Knife4j 配置类
 * @author weiyu
 * @date 2023/8/4
 */
@Configuration
public class Knife4jConfig {
  /*  @Bean
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
*/
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("遇见博客I")
                        .version("1.0")
                        .description( "系统简介")
                        .termsOfService("http://doc.xiaominfo.com")
                        .license(new License().name("Apache 2.0")
                                .url("http://doc.xiaominfo.com")));
    }
}
