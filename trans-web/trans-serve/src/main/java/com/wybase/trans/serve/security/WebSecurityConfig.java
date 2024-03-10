// package com.wybase.trans.serve.security;
//
// import jakarta.annotation.Resource;
// import org.springframework.context.annotation.Bean;
// import org.springframework.security.config.Customizer;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
// /**
//  * SpringSecurity配置文件
//  * 用于配置哪些请求被拦截，哪些请求可以匿名访问
//  *
//  * @author weiyu
//  * @date 2023/9/16
//  */
// @EnableWebSecurity
// public class WebSecurityConfig {
//     @Resource
//     private TokenAuthenticationFilter tokenAuthenticationFilter;
//     /*@Autowired
//     private TokenAuthenticationEntryPoint tokenAuthenticationEntryPoint;
//
//     @Autowired
//     private DynamicSecurityFilter dynamicSecurityFilter;
//     @Autowired
//     private DynamicAccessDeniedHandler accessDeniedHandler;*/
//
//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         //关闭 防止客户端的 csrf（跨站伪造） 攻击行为 的能力
//         // 从security过滤器链中撤出 CsrfFilter
//         http.csrf(AbstractHttpConfigurer::disable)
//                 .cors(AbstractHttpConfigurer::disable)
//                 //对所有请求按照以下约定进行拦截和放行
//                 .authorizeHttpRequests(
//                         //requestMatchers 指定匹配路径
//                         //permitAll 让security跳过之前通过requestMatchers匹配到的路径
//                         auth -> auth.requestMatchers(
//                                         "/base-trans/online/auth/login",
//                                         "/base-trans/doc.html",
//                                         "/base-trans/webjars/**",
//                                         "/base-trans/swagger-resources",
//                                         "/base-trans/swagger-resources/**",
//                                         "/base-trans/v2/api-docs",
//                                         "/base-trans/druid/**",
//                                         "/base-trans/favicon.ico")
//                                 .permitAll()
//                                 //anyRequest 指定除requestMatchers匹配路径之外的其他路径
//                                 //authenticated 让anyRequest匹配到的所有路径都通过security校验
//                                 .anyRequest().authenticated());
//         //将自定义的token认证过滤器加入到security-filterChian中，并指定其位置
//         http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//         http.headers(headers -> headers.cacheControl(HeadersConfigurer.CacheControlConfig::disable));
//         return http.build();
//
//         /*// 配置自定义JWT认证过滤器，验证token有效性
//         httpSecurity.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                 // 动态权限过滤器，用于实现基于路径的动态权限过滤
//                 .addFilterBefore(dynamicSecurityFilter, FilterSecurityInterceptor.class);
//
//         httpSecurity.exceptionHandling()
//                 .authenticationEntryPoint(tokenAuthenticationEntryPoint)
//                 .accessDeniedHandler(accessDeniedHandler);
//         // 禁用缓存
//         httpSecurity.headers().cacheControl();*/
//     }
//
// //    /**
// //     * 绕过spring security的所有filter
// //     *
// //     * @param web
// //     */
// //    @Override
// //    public void configure(WebSecurity web) {
// //        web.ignoring().antMatchers(
// //                "/online/auth/login",
// //                "/doc.html",
// //                "/webjars/**",
// //                "/swagger-resources",
// //                "/swagger-resources/**",
// //                "/v2/api-docs",
// //                "/druid/**",
// //                "/favicon.ico");
// //    }
// }
//
