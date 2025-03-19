package com.goodjob.common.config;

import com.goodjob.common.util.SecurityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

/**
 * Configuration for JPA auditing.
 * Enables automatic auditing of JPA entities with created/updated timestamps and user information.
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfig {

    /**
     * Provides the current auditor (user) for entity auditing.
     *
     * @return the current user ID or "system" if not authenticated
     */
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            String userId = SecurityUtils.getCurrentUserId();
            return Optional.ofNullable(userId).or(() -> Optional.of("system"));
        };
    }
} 