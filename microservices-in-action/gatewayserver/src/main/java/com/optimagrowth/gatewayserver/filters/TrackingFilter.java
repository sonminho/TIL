package com.optimagrowth.gatewayserver.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Order(1)
@Component
public class TrackingFilter implements GlobalFilter {

    @Autowired
    private FilterUtils filterUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("TrackingFilter started");
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();

        if(isCorrelationIdPresent(requestHeaders)) {
            log.info("tmx-correlation-id found in tracking filter: {}", filterUtils.getCorrelationId(requestHeaders));
        } else {
            String correlationID = generateCorrelationId();
            exchange = filterUtils.setCorrelationId(exchange, correlationID);
            log.info("tmx-correlation-id generated in tracking filter: {}", correlationID);
        }

        return chain.filter(exchange);
    }

    private String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }

    private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
        return filterUtils.getCorrelationId(requestHeaders) != null;
    }

}
