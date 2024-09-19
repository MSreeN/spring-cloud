package com.learn.gatewayserver.filters;

import org.jboss.logging.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class RequestTraceFilter implements GlobalFilter {

    @Autowired
    private FilterUtility filterUtility;

    public static final Logger log = Logger.getLogger(RequestTraceFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        if(isTransactionIdPresent(exchange.getRequest().getHeaders())){
            log.debug("Transaction Id is already present");
        }else{
            String transactionId = generateTransactionId();
            exchange = filterUtility.setTransactionId(exchange, transactionId);
            log.debug("added transactionId to the request");
        }
        //pass request to the next filter
        return chain.filter(exchange);
    }

    public boolean isTransactionIdPresent(HttpHeaders httpHeaders) {
        return filterUtility.getTransactionId(httpHeaders) != null;
    }

    public String generateTransactionId(){
        return UUID.randomUUID().toString();
    }
}
