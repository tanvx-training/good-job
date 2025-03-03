package com.goodjob.common.config;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for distributed tracing.
 * Sets up OpenTelemetry tracing.
 */
@Configuration
public class TracingConfig {

    /**
     * Creates an ObservedAspect for @Observed annotation support.
     *
     * @param observationRegistry the observation registry
     * @return the observed aspect
     */
    @Bean
    public ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        return new ObservedAspect(observationRegistry);
    }
} 