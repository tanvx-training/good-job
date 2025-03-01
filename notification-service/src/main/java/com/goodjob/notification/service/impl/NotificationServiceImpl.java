package com.goodjob.notification.service.impl;

import com.goodjob.notification.dto.NotificationCountResponse;
import com.goodjob.notification.dto.NotificationRequest;
import com.goodjob.notification.dto.NotificationResponse;
import com.goodjob.notification.entity.Notification;
import com.goodjob.notification.exception.NotificationNotFoundException;
import com.goodjob.notification.exception.UnauthorizedAccessException;
import com.goodjob.notification.repository.NotificationRepository;
import com.goodjob.notification.service.NotificationService;
import com.goodjob.notification.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of the NotificationService interface.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final WebSocketService webSocketService;

    /**
     * Creates a notification.
     *
     * @param request the notification request
     * @return the created notification response
     */
    @Override
    public NotificationResponse createNotification(NotificationRequest request) {
        log.info("Creating notification for user ID: {}", request.getUserId());
        
        Notification notification = Notification.builder()
                .userId(request.getUserId())
                .title(request.getTitle())
                .message(request.getMessage())
                .type(request.getType())
                .sourceId(request.getSourceId())
                .sourceType(request.getSourceType())
                .read(false)
                .actionUrl(request.getActionUrl())
                .build();
        
        notification = notificationRepository.save(notification);
        
        NotificationResponse response = NotificationResponse.fromEntity(notification);
        
        // Send notification to WebSocket
        webSocketService.sendNotification(response);
        
        log.info("Notification created with ID: {}", notification.getId());
        
        return response;
    }

    /**
     * Finds all notifications for a user.
     *
     * @param userId the user ID
     * @param pageable the pagination information
     * @return the page of notification responses
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NotificationResponse> findAllByUserId(String userId, Pageable pageable) {
        log.info("Finding all notifications for user ID: {}", userId);
        
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable)
                .map(NotificationResponse::fromEntity);
    }

    /**
     * Finds all unread notifications for a user.
     *
     * @param userId the user ID
     * @return the list of unread notification responses
     */
    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> findUnreadByUserId(String userId) {
        log.info("Finding unread notifications for user ID: {}", userId);
        
        return notificationRepository.findByUserIdAndReadFalseOrderByCreatedAtDesc(userId)
                .stream()
                .map(NotificationResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Gets the count of unread notifications for a user.
     *
     * @param userId the user ID
     * @return the notification count response
     */
    @Override
    @Transactional(readOnly = true)
    public NotificationCountResponse getUnreadCount(String userId) {
        log.info("Getting unread count for user ID: {}", userId);
        
        long count = notificationRepository.countByUserIdAndReadFalse(userId);
        
        return NotificationCountResponse.builder()
                .userId(userId)
                .unreadCount(count)
                .build();
    }

    /**
     * Marks a notification as read.
     *
     * @param id the notification ID
     * @param userId the user ID
     * @return the updated notification response
     */
    @Override
    public NotificationResponse markAsRead(UUID id, String userId) {
        log.info("Marking notification as read: {} for user ID: {}", id, userId);
        
        Notification notification = notificationRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new NotificationNotFoundException("Notification not found with ID: " + id));
        
        notification.setRead(true);
        notification = notificationRepository.save(notification);
        
        NotificationResponse response = NotificationResponse.fromEntity(notification);
        
        // Send updated notification count to WebSocket
        webSocketService.sendUnreadCount(getUnreadCount(userId));
        
        log.info("Notification marked as read: {}", id);
        
        return response;
    }

    /**
     * Marks all notifications as read for a user.
     *
     * @param userId the user ID
     * @return the number of updated notifications
     */
    @Override
    public int markAllAsRead(String userId) {
        log.info("Marking all notifications as read for user ID: {}", userId);
        
        int count = notificationRepository.markAllAsRead(userId);
        
        if (count > 0) {
            // Send updated notification count to WebSocket
            webSocketService.sendUnreadCount(NotificationCountResponse.builder()
                    .userId(userId)
                    .unreadCount(0)
                    .build());
        }
        
        log.info("{} notifications marked as read for user ID: {}", count, userId);
        
        return count;
    }

    /**
     * Deletes a notification.
     *
     * @param id the notification ID
     * @param userId the user ID
     */
    @Override
    public void deleteNotification(UUID id, String userId) {
        log.info("Deleting notification: {} for user ID: {}", id, userId);
        
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException("Notification not found with ID: " + id));
        
        if (!notification.getUserId().equals(userId)) {
            throw new UnauthorizedAccessException("User is not authorized to delete this notification");
        }
        
        notificationRepository.delete(notification);
        
        // If the notification was unread, update the unread count
        if (!notification.isRead()) {
            webSocketService.sendUnreadCount(getUnreadCount(userId));
        }
        
        log.info("Notification deleted: {}", id);
    }

    /**
     * Deletes all notifications for a user.
     *
     * @param userId the user ID
     */
    @Override
    public void deleteAllByUserId(String userId) {
        log.info("Deleting all notifications for user ID: {}", userId);
        
        notificationRepository.deleteByUserId(userId);
        
        // Send updated notification count to WebSocket
        webSocketService.sendUnreadCount(NotificationCountResponse.builder()
                .userId(userId)
                .unreadCount(0)
                .build());
        
        log.info("All notifications deleted for user ID: {}", userId);
    }
} 