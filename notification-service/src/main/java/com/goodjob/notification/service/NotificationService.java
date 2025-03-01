package com.goodjob.notification.service;

import com.goodjob.notification.dto.NotificationCountResponse;
import com.goodjob.notification.dto.NotificationRequest;
import com.goodjob.notification.dto.NotificationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for notification operations.
 */
public interface NotificationService {

    /**
     * Creates a notification.
     *
     * @param request the notification request
     * @return the created notification response
     */
    NotificationResponse createNotification(NotificationRequest request);

    /**
     * Finds all notifications for a user.
     *
     * @param userId the user ID
     * @param pageable the pagination information
     * @return the page of notification responses
     */
    Page<NotificationResponse> findAllByUserId(String userId, Pageable pageable);

    /**
     * Finds all unread notifications for a user.
     *
     * @param userId the user ID
     * @return the list of unread notification responses
     */
    List<NotificationResponse> findUnreadByUserId(String userId);

    /**
     * Gets the count of unread notifications for a user.
     *
     * @param userId the user ID
     * @return the notification count response
     */
    NotificationCountResponse getUnreadCount(String userId);

    /**
     * Marks a notification as read.
     *
     * @param id the notification ID
     * @param userId the user ID
     * @return the updated notification response
     */
    NotificationResponse markAsRead(UUID id, String userId);

    /**
     * Marks all notifications as read for a user.
     *
     * @param userId the user ID
     * @return the number of updated notifications
     */
    int markAllAsRead(String userId);

    /**
     * Deletes a notification.
     *
     * @param id the notification ID
     * @param userId the user ID
     */
    void deleteNotification(UUID id, String userId);

    /**
     * Deletes all notifications for a user.
     *
     * @param userId the user ID
     */
    void deleteAllByUserId(String userId);
} 