package com.learn.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class ResponseTranceFilter implements GlobalFilter {
    private static final Logger log = LoggerFactory.getLogger(ResponseTranceFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //then method indicates that the logic mentioned in then method executes once the actual
        // api returns the response.

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
            List<String> transactionIdFromRequest =
                    requestHeaders.get(FilterUtility.TRANSACTION_ID);
            String transactionId = transactionIdFromRequest.stream().findFirst().get();
            log.debug("fetched "+FilterUtility.TRANSACTION_ID+" from request",
                    transactionId);
            exchange.getResponse().getHeaders().add(FilterUtility.TRANSACTION_ID, transactionId);
                }
        ));
    }
}
