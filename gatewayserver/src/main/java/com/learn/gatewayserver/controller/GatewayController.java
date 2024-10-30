package com.learn.gatewayserver.controller;

import com.learn.gatewayserver.model.AccountsContactInfoDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class GatewayController {


    @GetMapping("/fb")
    @CircuitBreaker(name = "controllerCircuit", fallbackMethod = "cbFallback")
    public Mono<String> controllerWithFallback(){
        return WebClient.builder().baseUrl("http" +
                        "://localhost:8081/api" +
                        "/contactInfo").build()
                .get().retrieve().bodyToMono(AccountsContactInfoDto.class)
                .map(res -> res.message())
                .onErrorMap(e -> new RuntimeException("Service is down"));
    }

    public Mono<String> cbFallback(Throwable throwable){
        return Mono.just("Response from fallback method");
    }

    @GetMapping("/contactSupport")
    public String contactSupport(){
        return "Please contact support.";
    }
}
