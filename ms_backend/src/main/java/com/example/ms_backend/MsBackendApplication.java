package com.example.ms_backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@SpringBootApplication
public class MsBackendApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MsBackendApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

    }
}
