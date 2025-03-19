package com.goodjob.common.config;

import com.goodjob.common.util.SecurityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.util.Optional;

/**
 * Configuration for entity auditing.
 * Enables auditing for JPA and MongoDB entities.
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableMongoAuditing(auditorAwareRef = "auditorProvider")
public class AuditingConfig {

    /**
     * Creates an AuditorAware bean that provides the current user ID for auditing.
     *
     * @return the AuditorAware bean
     */
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            String userId = SecurityUtils.getCurrentUserId();
            return userId != null ? Optional.of(userId) : Optional.of("system");
        };
    }
} 