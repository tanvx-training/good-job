package com.goodjob.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Authorization Service Application for OAuth2 and JWT authentication.
 * This service handles user registration, login, and token management.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AuthorizationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServiceApplication.class, args);
    }
} 