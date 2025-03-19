package com.goodjob.common.config;

import com.goodjob.common.util.SecurityUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * Configuration for MongoDB entity auditing.
 * Only activated if MongoDB is on the classpath.
 */
@Configuration
@ConditionalOnClass(name = "org.springframework.data.mongodb.core.MongoTemplate")
public class MongoAuditingConfig {

    /**
     * Provides the current auditor (user) for document auditing.
     *
     * @return the current user ID or "system" if not authenticated
     */
    @Bean
    public AuditorAware<String> mongoAuditorProvider() {
        return () -> {
            String userId = SecurityUtils.getCurrentUserId();
            return Optional.ofNullable(userId).or(() -> Optional.of("system"));
        };
    }

    /**
     * MongoDB auditing configuration.
     * Uses the same auditor provider as JPA auditing.
     */
    @Configuration
    @ConditionalOnClass(name = "org.springframework.data.mongodb.config.EnableMongoAuditing")
    public static class EnableMongoAuditingConfig {
        
        // The @EnableMongoAuditing annotation would be here, but we're avoiding
        // direct dependencies on MongoDB classes to prevent class loading issues
        // when MongoDB is not on the classpath.
        //
        // Services that use MongoDB should include their own configuration:
        //
        // @Configuration
        // @EnableMongoAuditing(auditorAwareRef = "auditorProvider")
        // public class MyServiceMongoConfig {
        // }
    }
} 