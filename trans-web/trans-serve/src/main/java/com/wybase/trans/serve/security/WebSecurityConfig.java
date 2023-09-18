package com.wybase.trans.serve.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SpringSecurity配置文件
 * 用于配置哪些请求被拦截，哪些请求可以匿名访问
 * @author weiyu
 * @date 2023/9/16
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private TokenAuthenticationEntryPoint tokenAuthenticationEntryPoint;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;
    @Autowired
    private DynamicSecurityFilter dynamicSecurityFilter;
    @Autowired
    private DynamicAccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        //省略HttpSecurity的配置
        httpSecurity
                //允许跨域
                .cors()
                //关闭csrf
                .and().csrf().disable()
                .authorizeRequests()
                // 对于获取token的请求要允许匿名访问
                .antMatchers(
                        "/base-trans/online/auth/login",
                        "/base-trans/doc.html",
                        "/base-trans/webjars/**",
                        "/base-trans/swagger-resources",
                        "/base-trans/swagger-resources/**",
                        "/base-trans/v2/api-docs",
                        "/base-trans/favicon.ico")
                .permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        // 配置自定义JWT认证过滤器，验证token有效性
        httpSecurity.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // 动态权限过滤器，用于实现基于路径的动态权限过滤
                .addFilterBefore(dynamicSecurityFilter, FilterSecurityInterceptor.class);

        httpSecurity.exceptionHandling()
                .authenticationEntryPoint(tokenAuthenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
        // 禁用缓存
        httpSecurity.headers().cacheControl();
    }

    /**
     * 绕过spring security的所有filter
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/online/auth/login",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources",
                "/swagger-resources/**",
                "/v2/api-docs",
                "/favicon.ico");
    }
}
