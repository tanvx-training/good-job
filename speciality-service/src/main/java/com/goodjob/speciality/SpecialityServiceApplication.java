package com.goodjob.speciality;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main application class for the Speciality service.
 */
@SpringBootApplication
@EnableDiscoveryClient
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