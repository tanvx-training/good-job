package com.goodjob.job;

import com.goodjob.common.config.SharedConfigurationReference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main application class for the Job service.
 */
@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@Import(SharedConfigurationReference.class)
public class JobServiceApplication {

    /**
     * Main method to start the application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(JobServiceApplication.class, args);
    }
} 