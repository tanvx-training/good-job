//package com.goodjob.common.config;
//
//import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
//import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
//import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
//import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
//import org.springframework.cloud.client.circuitbreaker.Customizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.Duration;
//
///**
// * Configuration for circuit breakers used with Feign clients.
// * Provides resilience patterns for inter-service communication.
// */
//@Configuration
//public class CircuitBreakerConfig {
//
//    /**
//     * Configures the default circuit breaker settings.
//     *
//     * @param circuitBreakerRegistry the circuit breaker registry
//     * @param timeLimiterRegistry the time limiter registry
//     * @return a customizer for the Resilience4J circuit breaker factory
//     */
//    @Bean
//    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer(
//            CircuitBreakerRegistry circuitBreakerRegistry,
//            TimeLimiterRegistry timeLimiterRegistry) {
//
//        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
//                .circuitBreakerConfig(circuitBreakerRegistry.getDefaultConfig())
//                .timeLimiterConfig(timeLimiterRegistry.getDefaultConfig()
//                        .toBuilder()
//                        .timeoutDuration(Duration.ofSeconds(3))
//                        .build())
//                .build());
//    }
//
//    /**
//     * Configures a specific circuit breaker for notification service.
//     *
//     * @param circuitBreakerRegistry the circuit breaker registry
//     * @param timeLimiterRegistry the time limiter registry
//     * @return a customizer for the Resilience4J circuit breaker factory
//     */
//    @Bean
//    public Customizer<Resilience4JCircuitBreakerFactory> notificationServiceCustomizer(
//            CircuitBreakerRegistry circuitBreakerRegistry,
//            TimeLimiterRegistry timeLimiterRegistry) {
//
//        return factory -> factory.configure(builder -> builder
//                .circuitBreakerConfig(circuitBreakerRegistry.getDefaultConfig())
//                .timeLimiterConfig(timeLimiterRegistry.getDefaultConfig()
//                        .toBuilder()
//                        .timeoutDuration(Duration.ofSeconds(2))
//                        .build()),
//                "notification-service");
//    }
//}