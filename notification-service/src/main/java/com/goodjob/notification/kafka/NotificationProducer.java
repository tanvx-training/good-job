package com.goodjob.notification.kafka;

import com.goodjob.notification.dto.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * Kafka producer for notification events.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${notification.kafka.topic}")
    private String topic;

    /**
     * Sends a notification event to Kafka.
     *
     * @param notificationRequest the notification request
     * @return a CompletableFuture of the send result
     */
    public CompletableFuture<SendResult<String, Object>> sendNotification(NotificationRequest notificationRequest) {
        log.info("Sending notification event to topic {}: {}", topic, notificationRequest);
        
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(
                topic, 
                notificationRequest.getRecipientId(), 
                notificationRequest
        );
        
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Notification sent successfully: {} offset: {}", 
                        notificationRequest.getRecipientId(),
                        result.getRecordMetadata().offset());
            } else {
                log.error("Failed to send notification: {}", ex.getMessage(), ex);
            }
        });
        
        return future;
    }
} 