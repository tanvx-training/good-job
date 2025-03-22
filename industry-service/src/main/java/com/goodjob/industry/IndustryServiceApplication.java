package com.goodjob.industry;

import com.goodjob.common.config.SharedConfigurationReference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

/**
 * Main application class for the Industry Service.
 */
@SpringBootApplication
@EnableDiscoveryClient
@Import(SharedConfigurationReference.class)
public class IndustryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(IndustryServiceApplication.class, args);
    }
} 