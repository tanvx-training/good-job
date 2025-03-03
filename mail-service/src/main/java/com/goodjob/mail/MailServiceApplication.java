package com.goodjob.mail;

import com.goodjob.common.config.OpenFeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main application class for the Mail Service.
 * This service handles transactional emails for the Good Job platform.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableMongoAuditing
@EnableKafka
@EnableAsync
@EnableScheduling
@Import(OpenFeignConfig.class)
public class MailServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailServiceApplication.class, args);
    }
} 