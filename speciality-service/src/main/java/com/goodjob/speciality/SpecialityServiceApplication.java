package com.goodjob.speciality;

import com.goodjob.common.config.SharedConfigurationReference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

/**
 * Main application class for the Speciality service.
 */
@SpringBootApplication
@EnableDiscoveryClient
@Import(SharedConfigurationReference.class)
public class SpecialityServiceApplication {

    /**
     * Main method to start the application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(SpecialityServiceApplication.class, args);
    }
} 