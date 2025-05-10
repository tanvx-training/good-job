package com.goodjob.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main application class for the Notification Service.
 * This service manages notifications, preferences, and delivery channels.
 */
@SpringBootApplication
@EnableFeignClients
@EnableKafka
@EnableScheduling
public class NotificationServiceApplication {

    /**
     * Main method to start the application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
} 