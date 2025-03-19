package com.goodjob.common.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;

import java.util.stream.Collectors;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                String username = authentication.getName();
                if (username != null) {
                    requestTemplate.header("X-Auth-Username", username);
                }

                String roles = authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .filter(authority -> authority.startsWith("ROLE_"))
                        .collect(Collectors.joining(","));
                String permissions = authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .filter(authority -> !authority.startsWith("ROLE_"))
                        .collect(Collectors.joining(","));

                if (!roles.isEmpty()) {
                    requestTemplate.header("X-Auth-Roles", roles);
                }
                if (!permissions.isEmpty()) {
                    requestTemplate.header("X-Auth-Permissions", permissions);
                }
            }
        };
    }
}