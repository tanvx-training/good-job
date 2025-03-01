package com.goodjob.candidate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main application class for the Candidate Service.
 * This service manages candidate profiles, skills, experiences, and applications.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CandidateServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CandidateServiceApplication.class, args);
    }
} 