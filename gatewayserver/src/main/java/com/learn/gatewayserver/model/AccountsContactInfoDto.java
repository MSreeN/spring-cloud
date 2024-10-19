package com.learn.gatewayserver.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

public record AccountsContactInfoDto(String message, Map<String, String> contactDetails,
                                     List<String> onCallSupport) {
}

//record ContactDetails(String name, String email){}