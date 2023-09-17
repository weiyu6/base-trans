package com.wybase.trans.serve.security;

import com.wybase.trans.serve.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.Map;

/**
 * 动态权限数据源，用于获取动态权限规则
 * @author weiyu
 * @date 2023/9/17
 */
@Component
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private static Map<String, ConfigAttribute> configAttributeMap = null;
    //Java自带的路径匹配工具
    AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    private IMenuService menuService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();//获取请求地址
        return SecurityConfig.createList(requestUrl);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
