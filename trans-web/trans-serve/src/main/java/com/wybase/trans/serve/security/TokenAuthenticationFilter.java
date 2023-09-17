package com.wybase.trans.serve.security;

import com.wybase.trans.common.consts.TransHeardConsts;
import com.wybase.trans.common.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT认证过滤器 【验证token有效性】
 * @author weiyu
 * @date 2023/9/16
 */
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);
    @Value("${util.properties.tokenSignKey:m1e2e3t4b5l6o7g8}")
    private String tokenSignKey;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException, UsernameNotFoundException {
        logger.debug("TokenAuthenticationFilter.doFilterInternal begin >>>>>>>>>>>>>>>>>>>");
        // 从请求头获取token
        String tokenHeader = request.getHeader(TransHeardConsts.TOKEN_HEAD);
        logger.debug("tokenHeader:{}", tokenHeader);
        if (StringUtils.isNotEmpty(tokenHeader)) {
            String userId = JwtUtil.getUserId(tokenHeader, tokenSignKey);
            String userName = JwtUtil.getUserName(tokenHeader, tokenSignKey);
            request.setAttribute("TokenUserId", userId);
            request.setAttribute("TokenUserName", userName);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            // 将用户相关的信息存放到系统的安全上下文中
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        chain.doFilter(request, response);
    }
}
