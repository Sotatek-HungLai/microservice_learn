package com.example.ms_auth_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
//@EnableDiscoveryClient
public class MsAuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsAuthServiceApplication.class, args);
    }

}
