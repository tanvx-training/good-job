package com.goodjob.industry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main application class for the Industry Service.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class IndustryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(IndustryServiceApplication.class, args);
    }
} 