package com.learn.gatewayserver.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

public class FilterUtility {

    public static final String TRANSACTION_ID = "transactionId";

    public String getTransactionId(HttpHeaders httpHeaders){
        if(httpHeaders.get(TRANSACTION_ID) != null){
            List<String> headerValues =  httpHeaders.get(TRANSACTION_ID);
            return headerValues.stream().findFirst().get();
        }
        else {
            return null;
        }
    }

    public ServerWebExchange setTransactionId(ServerWebExchange exchange, String transactionId){
        return exchange.mutate().request(exchange.getRequest().mutate().header(TRANSACTION_ID,
                transactionId).build()).build();
    }
}
