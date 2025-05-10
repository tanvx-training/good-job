package com.goodjob.notification.infrastructure.messaging.websocket;

import com.goodjob.notification.domain.notification.dto.NotificationDTO;

/**
 * Service for sending notifications through WebSocket.
 */
public interface WebSocketService {
    
    void sendNotification(String userId, NotificationDTO notification);
} 