package com.goodjob.benefit;

import com.goodjob.common.config.SharedConfigurationReference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

/**
 * Main application class for the Benefit Service.
 * Manages job-related benefits for the Good Job platform.
 */
@SpringBootApplication
@EnableDiscoveryClient
@Import(SharedConfigurationReference.class)
public class BenefitServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BenefitServiceApplication.class, args);
    }
} 