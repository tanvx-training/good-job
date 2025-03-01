package com.goodjob.benefit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main application class for the Benefit Service.
 * Manages job-related benefits for the Good Job platform.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class BenefitServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BenefitServiceApplication.class, args);
    }
} 