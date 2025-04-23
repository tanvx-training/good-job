package com.goodjob.job;

import com.goodjob.common.infrastructure.config.SharedConfigurationReference;
import com.goodjob.common.api.feign.config.CustomFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main application class for the Job service.
 */
@SpringBootApplication
@CustomFeignClients
@EnableCaching
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