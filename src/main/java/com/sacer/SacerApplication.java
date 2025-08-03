package com.sacer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SacerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SacerApplication.class, args);
    }
} 