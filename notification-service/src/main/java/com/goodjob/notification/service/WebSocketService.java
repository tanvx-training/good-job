package com.goodjob.notification.service;

import com.goodjob.notification.dto.NotificationCountResponse;
import com.goodjob.notification.dto.NotificationResponse;

/**
 * Service interface for WebSocket operations.
 */
public interface WebSocketService {

    /**
     * Sends a notification to a user via WebSocket.
     *
     * @param notification the notification response
     */
    void sendNotification(NotificationResponse notification);

    /**
     * Sends the unread notification count to a user via WebSocket.
     *
     * @param countResponse the notification count response
     */
    void sendUnreadCount(NotificationCountResponse countResponse);
} 