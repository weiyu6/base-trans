package com.wybase.trans.serve.security;

import com.alibaba.fastjson2.JSONObject;
import com.wybase.trans.base.result.ResultCodeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 未授权的统一处理方式
 * @author weiyu
 * @date 2023/9/17
 */
@Component
public class DynamicAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setStatus(HttpStatus.OK.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String message = e.getMessage();
        Map<String, Object> result = new HashMap<>();
        result.put("code", ResultCodeEnum.NO_ACCREDIT.getCode());
        result.put("msg", message);
        result.put("data", "授权失败");
        response.getWriter().write(JSONObject.toJSONString(result));
    }
}
