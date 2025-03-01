package com.goodjob.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Main application class for the Mail Service.
 * This service handles transactional emails for the Good Job platform.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableKafka
public class MailServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailServiceApplication.class, args);
    }
} 