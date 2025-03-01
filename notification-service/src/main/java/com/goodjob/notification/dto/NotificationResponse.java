package com.goodjob.notification.dto;

import com.goodjob.notification.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for returning notification data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {

    private UUID id;
    private String userId;
    private String title;
    private String message;
    private Notification.NotificationType type;
    private String sourceId;
    private Notification.SourceType sourceType;
    private boolean read;
    private String actionUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Creates a NotificationResponse from a Notification entity.
     *
     * @param notification the notification entity
     * @return the notification response
     */
    public static NotificationResponse fromEntity(Notification notification) {
        if (notification == null) {
            return null;
        }

        return NotificationResponse.builder()
                .id(notification.getId())
                .userId(notification.getUserId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .type(notification.getType())
                .sourceId(notification.getSourceId())
                .sourceType(notification.getSourceType())
                .read(notification.isRead())
                .actionUrl(notification.getActionUrl())
                .createdAt(notification.getCreatedAt())
                .updatedAt(notification.getUpdatedAt())
                .build();
    }
} 