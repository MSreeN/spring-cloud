package com.eazybytes.accounts.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ConfigurationProperties(prefix = "accounts")
public record AccountsContactInfoDto(String message, ContactDetails contactDetails,
                                     List<String> num) {

}

record ContactDetails(String name, String email){}