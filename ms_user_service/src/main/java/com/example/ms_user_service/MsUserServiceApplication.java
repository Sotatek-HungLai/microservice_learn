package com.example.ms_user_service;

import com.example.ms_user_service.configs.PermitAllRequests;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class MsUserServiceApplication implements CommandLineRunner {

    private final PermitAllRequests permitAllRequests;

    public static void main(String[] args) {
        SpringApplication.run(MsUserServiceApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("get: " + permitAllRequests.getGetList().toString());
        System.out.println("post: " + permitAllRequests.getPostList().toString());
    }
}
