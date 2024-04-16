package com.wybase.trans.serve.aspect;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wybase.trans.common.consts.TransConsts;
import com.wybase.trans.serve.util.Sm2Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author weiyu
 * @date 2024/4/2
 */
@Component
@ControllerAdvice
public class ResponseHandler implements ResponseBodyAdvice<Object> {
    @Value("${util.sm2.publicKey}")
    private String publicKey;

    @Value("${util.sm2.encryptType}")
    private String encryptType;

    /**
     * 返回true，才会走beforeBodyWrite方法
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    /**
     * 响应加密
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest request, ServerHttpResponse serverHttpResponse) {
        if (StringUtils.equals(TransConsts.ENCRYPT_TYPE_0, encryptType)) {
            // 拿到响应的数据
            String json = JSON.toJSONString(body);
            // 进行加密
            String encrypt = Sm2Util.encrypt(publicKey, json);
            JSONObject object = new JSONObject();
            object.put("encryptData", encrypt);
            return object;
        }
        return body;

    }
}
