package com.learn.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

import java.time.LocalTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	public RouteLocator gatewayConfig(RouteLocatorBuilder routeLocatorBuilder){
		return routeLocatorBuilder.routes()
				.route(r -> r.path("/gateway/accounts/**")
						.filters(f -> f.rewritePath("/gateway/accounts/(?<remaining>.*)",
										"/${remaining}")
								.addRequestHeader("time", LocalTime.now().toString()))
						.uri("lb://ACCOUNTS")).build();
	}

}
