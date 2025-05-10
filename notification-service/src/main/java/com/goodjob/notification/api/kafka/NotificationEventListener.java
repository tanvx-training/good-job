package com.goodjob.notification.api.kafka;

import com.goodjob.notification.domain.notification.dto.NotificationEvent;
import com.goodjob.notification.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka listener for notification events.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationService notificationService;

    @KafkaListener(topics = "${notification.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(NotificationEvent event) {
        log.info("Received notification event: {}", event);
        try {
            notificationService.processNotificationEvent(event);
        } catch (Exception e) {
            log.error("Error processing notification event: {}", e.getMessage(), e);
        }
    }
} 