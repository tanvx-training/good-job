package com.goodjob.mail.service;

import com.goodjob.common.dto.ApiResponse;
import com.goodjob.common.feign.NotificationServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service for sending notifications about email events.
 * Uses the notification service client to send notifications.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationService {

    private final NotificationServiceClient notificationServiceClient;
    private final CircuitBreakerFactory circuitBreakerFactory;

    /**
     * Sends a notification about an email event.
     *
     * @param userId the user ID
     * @param emailId the email ID
     * @param subject the email subject
     * @param eventType the event type (e.g., "SENT", "DELIVERED", "OPENED")
     * @return true if the notification was sent successfully, false otherwise
     */
    public boolean sendEmailEventNotification(String userId, String emailId, String subject, String eventType) {
        log.info("Sending email event notification to user {}, emailId: {}, eventType: {}", userId, emailId, eventType);
        
        Map<String, Object> notification = new HashMap<>();
        notification.put("userId", userId);
        notification.put("type", "EMAIL_" + eventType);
        notification.put("title", "Email " + eventType.toLowerCase());
        notification.put("content", "Your email '" + subject + "' has been " + eventType.toLowerCase());
        notification.put("emailId", emailId);
        
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("notificationService");
        
        try {
            ApiResponse<Map<String, Object>> response = circuitBreaker.run(
                    () -> notificationServiceClient.createNotification(notification),
                    throwable -> {
                        log.error("Error sending email event notification", throwable);
                        return ApiResponse.error("Failed to send email event notification");
                    }
            );
            
            if (response.isSuccess()) {
                log.info("Email event notification sent successfully to user {}, emailId: {}, eventType: {}", 
                        userId, emailId, eventType);
                return true;
            } else {
                log.warn("Failed to send email event notification: {}", response.getMessage());
                return false;
            }
        } catch (Exception e) {
            log.error("Unexpected error sending email event notification", e);
            return false;
        }
    }
} 