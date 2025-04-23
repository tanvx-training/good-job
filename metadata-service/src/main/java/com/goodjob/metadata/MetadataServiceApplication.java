package com.goodjob.metadata;

import com.goodjob.common.infrastructure.config.SharedConfigurationReference;
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
public class MetadataServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetadataServiceApplication.class, args);
    }
} 