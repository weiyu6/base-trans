package com.wybase.trans.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 参数解析过滤器
 * @author weiyu
 * @date 2024/4/13
 */
@Component
public class ParamFilter implements GlobalFilter, Ordered {
    private static final Logger logger = LoggerFactory.getLogger(ParamFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // 获取客户端IP地址
        String ip = request.getRemoteAddress() != null ? request.getRemoteAddress().getAddress().getHostAddress() : "Unknown";

        // 记录IP和请求行
        logger.info("Request from IP: {}, Method: {}, Path: {}", ip, request.getMethod(), request.getPath());

        String realIp = request.getHeaders().getFirst("X-Forwarded-For");

        logger.info("X-Forwarded-For: {}", realIp);
        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
