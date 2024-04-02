package com.wybase.trans.serve.aspect;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wybase.trans.serve.util.RSAUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import java.io.IOException;

import static com.wybase.trans.serve.util.RSAUtils.privateKey;

/**
 * 请求加解密过滤器
 *
 * @author 猴哥
 */
@Component
public class RequestHandler implements Filter {
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

        // 拿到加密串
        String data = new RequestWrapper((HttpServletRequest) request).getBody();
        if (StringUtils.isEmpty(data)) {
            chain.doFilter(request, response);
            return;
        }
        JSONObject jsonObject = JSONObject.parseObject(data);
        data = jsonObject.getString("data");
        // 解析
        byte[] decryptStrByte = new byte[0];
        try {
            decryptStrByte = RSAUtils.decryptByPrivateKey(Base64.decodeBase64("GORVsjl3YH4t3ZrNxmhrwEI6QD7wJOAnpLRBBqWXNqhcOpTP649dPSmN4G2j08DdRADy5y2crHno3v0Fsb7KdhR4+dRLA5YdikyqTR4lNhRbt2EbMB7MsJBbcHylu+QrbJ4GMMcrtxDGxgT3R8OXCsqqSp65UJSAwUYNshhH4d4="), privateKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String body = new String(decryptStrByte);
        request = new BodyRequestWrapper((HttpServletRequest) request, body);
        chain.doFilter(request, response);
    }
}
