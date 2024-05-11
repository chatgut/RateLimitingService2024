package com.iths.ratelimitingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.iths")  // Adjust this to include all necessary packages
public class RateLimitingService2024Application {
    public static void main(String[] args) {
        SpringApplication.run(RateLimitingService2024Application.class, args);
    }
}