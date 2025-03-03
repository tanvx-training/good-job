package com.goodjob.common.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for metrics collection.
 * Sets up Micrometer and Prometheus metrics.
 */
@Configuration
public class MetricsConfig {

    /**
     * Customizes the meter registry with common tags.
     *
     * @param applicationName the application name
     * @return the customizer
     */
    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags(
            @Value("${spring.application.name:unknown}") String applicationName) {
        return registry -> registry.config()
                .commonTags(Tags.of(
                        Tag.of("application", applicationName),
                        Tag.of("environment", "${spring.profiles.active:default}")
                ));
    }

    /**
     * Creates a TimedAspect for @Timed annotation support.
     *
     * @param registry the meter registry
     * @return the timed aspect
     */
    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }
} 