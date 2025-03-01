package com.goodjob.notification.kafka;

import com.goodjob.notification.dto.NotificationRequest;
import com.goodjob.notification.dto.NotificationResponse;
import com.goodjob.notification.service.NotificationService;
import com.goodjob.notification.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka consumer for notification events.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationService notificationService;
    private final WebSocketService webSocketService;

    /**
     * Listens for notification events and processes them.
     *
     * @param notificationRequest the notification request
     */
    @KafkaListener(topics = "${notification.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeNotification(NotificationRequest notificationRequest) {
        log.info("Received notification event: {}", notificationRequest);
        
        try {
            // Create notification in the database
            NotificationResponse notification = notificationService.createNotification(notificationRequest);
            
            // Send notification to user via WebSocket
            webSocketService.sendNotification(notification);
            
            // Send updated unread count to user via WebSocket
            webSocketService.sendUnreadCount(
                notificationService.getUnreadCount(notification.getRecipientId())
            );
            
            log.info("Notification processed successfully: {}", notification.getId());
        } catch (Exception e) {
            log.error("Error processing notification: {}", e.getMessage(), e);
        }
    }
} 