package com.wybase.trans.serve.aspect;

import com.alibaba.fastjson2.JSONObject;
import com.wybase.trans.serve.util.Sm2Util;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 请求加解密过滤器
 * @author 猴哥
 */
@Component
public class RequestHandler implements Filter {

    @Value("${util.sm2.privateKey}")
    private String privateKey;

    /**
     * 进行请求加密
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // form-data不校验
        if ("application/x-www-form-urlencoded".equals(request.getContentType())) {
            chain.doFilter(request, response);
            return;
        }
        if (StringUtils.contains(request.getContentType(), "multipart/form-data")) {
            chain.doFilter(request, response);
            return;
        }
        // 拿到加密串
        String data = new RequestWrapper((HttpServletRequest) request).getBody();
        if (StringUtils.isBlank(data)) {
            request = new BodyRequestWrapper((HttpServletRequest) request, data);
            chain.doFilter(request, response);
            return;
        }
        JSONObject jsonObject = JSONObject.parseObject(data);
        String encryptData = jsonObject.getString("encryptData");
        // 获取加密数据字段，如果此字段为空，表示数据位非加密，则不进行解密
        if (StringUtils.isBlank(encryptData)) {
            request = new BodyRequestWrapper((HttpServletRequest) request, data);
            chain.doFilter(request, response);
            return;
        }
        // 对数据进行解密，获取原始报文
        String body = Sm2Util.decrypt(privateKey, encryptData);
        request = new BodyRequestWrapper((HttpServletRequest) request, body);
        chain.doFilter(request, response);
    }
}
