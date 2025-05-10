package com.goodjob.notification.infrastructure.messaging.email;

import com.goodjob.notification.domain.notification.dto.NotificationEvent;

/**
 * Service for sending email notifications.
 */
public interface EmailService {
    
    void sendNotificationEmail(NotificationEvent event);
} 