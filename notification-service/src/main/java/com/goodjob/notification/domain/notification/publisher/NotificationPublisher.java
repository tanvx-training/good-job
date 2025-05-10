package com.goodjob.notification.domain.notification.publisher;

import com.goodjob.notification.domain.notification.dto.NotificationEvent;

/**
 * Interface for publishing notification events.
 */
public interface NotificationPublisher {
    
    void publishNotificationEvent(NotificationEvent event);
} 