package com.example.ms_auth_service.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class CustomProperties {
    String secret;
    Long expiration;

}