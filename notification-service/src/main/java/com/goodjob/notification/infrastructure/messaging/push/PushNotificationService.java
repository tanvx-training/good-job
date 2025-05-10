package com.goodjob.notification.infrastructure.messaging.push;

import com.goodjob.notification.domain.notification.dto.NotificationEvent;

/**
 * Service for sending push notifications.
 */
public interface PushNotificationService {
    
    void sendPushNotification(NotificationEvent event);
} 