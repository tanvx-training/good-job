package com.goodjob.notification.service;

import com.goodjob.common.dto.ApiResponse;
import com.goodjob.common.feign.MailServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service for sending email notifications.
 * Uses the mail service client to send emails.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationEmailService {

    private final MailServiceClient mailServiceClient;
    private final CircuitBreakerFactory circuitBreakerFactory;

    /**
     * Sends an email notification.
     *
     * @param to the recipient email address
     * @param subject the email subject
     * @param content the email content
     * @param notificationId the notification ID
     * @return true if the email was sent successfully, false otherwise
     */
    public boolean sendEmailNotification(String to, String subject, String content, String notificationId) {
        log.info("Sending email notification to {}, subject: {}, notificationId: {}", to, subject, notificationId);
        
        Map<String, Object> emailRequest = new HashMap<>();
        emailRequest.put("to", to);
        emailRequest.put("subject", subject);
        emailRequest.put("content", content);
        emailRequest.put("notificationId", notificationId);
        
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("mailService");
        
        try {
            ApiResponse<Map<String, Object>> response = circuitBreaker.run(
                    () -> mailServiceClient.sendEmail(emailRequest),
                    throwable -> {
                        log.error("Error sending email notification", throwable);
                        return ApiResponse.error("Failed to send email notification");
                    }
            );
            
            if (response.isSuccess()) {
                log.info("Email notification sent successfully to {}, notificationId: {}", to, notificationId);
                return true;
            } else {
                log.warn("Failed to send email notification: {}", response.getMessage());
                return false;
            }
        } catch (Exception e) {
            log.error("Unexpected error sending email notification", e);
            return false;
        }
    }
} 