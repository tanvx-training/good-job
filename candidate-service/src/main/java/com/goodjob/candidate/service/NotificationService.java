package com.goodjob.candidate.service;

import com.goodjob.candidate.client.NotificationClient;
import com.goodjob.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service for sending notifications from the candidate service.
 * Uses the notification client to communicate with the notification service.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationClient notificationClient;

    /**
     * Sends a notification to a user.
     *
     * @param userId the user ID
     * @param title the notification title
     * @param message the notification message
     * @param type the notification type
     * @return true if the notification was sent successfully, false otherwise
     */
    public boolean sendNotification(String userId, String title, String message, String type) {
        log.info("Sending notification to user {}: {}", userId, title);
        
        Map<String, Object> notification = new HashMap<>();
        notification.put("userId", userId);
        notification.put("title", title);
        notification.put("message", message);
        notification.put("type", type);
        
        try {
            ApiResponse<Map<String, Object>> response = notificationClient.createNotification(notification);
            log.info("Notification sent: {}", response.isSuccess());
            return response.isSuccess();
        } catch (Exception e) {
            log.error("Failed to send notification", e);
            return false;
        }
    }

    /**
     * Gets notifications for a user.
     *
     * @param userId the user ID
     * @return the user's notifications
     */
    public List<Map<String, Object>> getUserNotifications(String userId) {
        log.info("Getting notifications for user {}", userId);
        
        try {
            ApiResponse<List<Map<String, Object>>> response = notificationClient.getUserNotifications(userId);
            if (response.isSuccess()) {
                return response.getData();
            } else {
                log.warn("Failed to get notifications: {}", response.getMessage());
                return List.of();
            }
        } catch (Exception e) {
            log.error("Failed to get notifications", e);
            return List.of();
        }
    }

    /**
     * Marks a notification as read.
     *
     * @param notificationId the notification ID
     * @return true if the notification was marked as read successfully, false otherwise
     */
    public boolean markNotificationAsRead(String notificationId) {
        log.info("Marking notification {} as read", notificationId);
        
        try {
            ApiResponse<Map<String, Object>> response = notificationClient.markNotificationAsRead(notificationId);
            log.info("Notification marked as read: {}", response.isSuccess());
            return response.isSuccess();
        } catch (Exception e) {
            log.error("Failed to mark notification as read", e);
            return false;
        }
    }
} 