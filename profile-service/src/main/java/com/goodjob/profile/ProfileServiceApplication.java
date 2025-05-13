package com.goodjob.profile;

import com.goodjob.common.api.feign.config.CustomFeignClients;
import com.goodjob.common.infrastructure.config.SharedConfigurationReference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

/**
 * Main application class for the Profile Service.
 * This service manages user profiles, experiences, education, skills, certifications, and projects.
 */
@SpringBootApplication
@CustomFeignClients
@EnableCaching
@Import(SharedConfigurationReference.class)
public class ProfileServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfileServiceApplication.class, args);
    }
} 