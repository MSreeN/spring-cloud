package com.learn.gatewayserver.config;


import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CircuitBreakerConfig {

    @Bean
    public CircuitBreaker customCircuitBreaker(){
        io.github.resilience4j.circuitbreaker.CircuitBreakerConfig circuitBreaker = io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.custom()
                .slidingWindowSize(10)
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(10000))
                .permittedNumberOfCallsInHalfOpenState(2)
                .minimumNumberOfCalls(1)
                .build();

        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(circuitBreaker);
        return registry.circuitBreaker("custom");
    }
}
