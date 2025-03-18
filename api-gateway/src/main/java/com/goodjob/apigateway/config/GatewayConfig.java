package com.goodjob.apigateway.config;

import com.goodjob.apigateway.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

    return builder.routes()
            .route("benefit-service", r -> r.path("/api/v1/benefits/**")
                    .filters(f -> f.filter(jwtAuthenticationFilter))
                    .uri("lb://benefit-service"))
            .route("candidate-service", r -> r.path("/api/v1/candidates/**")
                    .filters(f -> f.filter(jwtAuthenticationFilter))
                    .uri("lb://candidate-service"))
            .route("company-service", r -> r.path("/api/v1/companies/**")
                    .filters(f -> f.filter(jwtAuthenticationFilter))
                    .uri("lb://company-service"))
            .route("industry-service", r -> r.path("/api/v1/industries/**")
                    .filters(f -> f.filter(jwtAuthenticationFilter))
                    .uri("lb://industry-service"))
            .route("job-service", r -> r.path("/api/v1/jobs/**")
                    .filters(f -> f.filter(jwtAuthenticationFilter))
                    .uri("lb://job-service"))
            .route("mail-service", r -> r.path("/api/v1/mails/**")
                    .filters(f -> f.filter(jwtAuthenticationFilter))
                    .uri("lb://mail-service"))
            .route("notification-service", r -> r.path("/api/v1/notifications/**")
                    .filters(f -> f.filter(jwtAuthenticationFilter))
                    .uri("lb://notifications-service"))
            .route("posting-service", r -> r.path("/api/v1/postings/**")
                    .filters(f -> f.filter(jwtAuthenticationFilter))
                    .uri("lb://posting-service"))
            .route("skill-service", r -> r.path("/api/v1/skills/**")
                    .filters(f -> f.filter(jwtAuthenticationFilter))
                    .uri("lb://skill-service"))
            .route("speciality-service", r -> r.path("/api/v1/specialities/**")
                .filters(f -> f.filter(jwtAuthenticationFilter))
                .uri("lb://speciality-service"))
            .build();
  }
}
