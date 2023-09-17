package com.wybase.trans.serve.security;

import com.alibaba.fastjson2.JSONObject;
import com.wybase.trans.base.result.ResultCodeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证失败的统一处理方式
 * @author weiyu
 * @date 2023/9/16
 */
@Component
public class TokenAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setStatus(HttpStatus.OK.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        Map<String, Object> result = new HashMap<>();
        result.put("code", ResultCodeEnum.NO_AUTH.getCode());
        result.put("msg", ResultCodeEnum.NO_AUTH.getMsg());
        result.put("data", "token无效或过期,请重新登录");
        response.getWriter().write(JSONObject.toJSONString(result));
    }
}
