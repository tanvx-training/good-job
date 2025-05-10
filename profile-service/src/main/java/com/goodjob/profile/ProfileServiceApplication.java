package com.goodjob.profile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Main application class for the Profile Service.
 * This service manages user profiles, experiences, education, skills, certifications, and projects.
 */
@SpringBootApplication
@EnableFeignClients
public class ProfileServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfileServiceApplication.class, args);
    }
} 