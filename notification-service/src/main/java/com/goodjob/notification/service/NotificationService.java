package com.goodjob.notification.service;

import com.goodjob.common.dto.ApiResponse;
import com.goodjob.common.exception.ServiceException;
import com.goodjob.common.feign.MailServiceClient;
import com.goodjob.notification.dto.NotificationCountResponse;
import com.goodjob.notification.dto.NotificationRequest;
import com.goodjob.notification.dto.NotificationResponse;
import com.goodjob.notification.model.Notification;
import com.goodjob.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Service interface for notification operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final MailServiceClient mailServiceClient;

    /**
     * Creates a notification.
     *
     * @param request the notification request
     * @return the created notification response
     */
    public NotificationResponse createNotification(NotificationRequest request) {
        Notification notification = Notification.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .userId(request.getUserId())
                .userEmail(request.getUserEmail())
                .build();

        Notification savedNotification = notificationRepository.save(notification);
        
        boolean sendEmail = request.isSendEmail();
        if (sendEmail) {
            try {
                sendEmailNotification(savedNotification);
            } catch (Exception e) {
                log.error("Failed to send email notification", e);
                // Continue even if email sending fails
            }
        }
        
        return NotificationResponse.fromNotification(savedNotification);
    }

    /**
     * Finds all notifications for a user.
     *
     * @param userId the user ID
     * @param pageable the pagination information
     * @return the page of notification responses
     */
    public Page<NotificationResponse> findAllByUserId(String userId, Pageable pageable) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable)
                .map(NotificationResponse::fromNotification);
    }

    /**
     * Finds all unread notifications for a user.
     *
     * @param userId the user ID
     * @return the list of unread notification responses
     */
    public List<NotificationResponse> findUnreadByUserId(String userId) {
        return notificationRepository.findByUserIdAndReadFalseOrderByCreatedAtDesc(userId)
                .stream()
                .map(NotificationResponse::fromNotification)
                .toList();
    }

    /**
     * Gets the count of unread notifications for a user.
     *
     * @param userId the user ID
     * @return the notification count response
     */
    public NotificationCountResponse getUnreadCount(String userId) {
        long count = notificationRepository.countByUserIdAndReadFalse(userId);
        return new NotificationCountResponse(count);
    }

    /**
     * Marks a notification as read.
     *
     * @param id the notification ID
     * @param userId the user ID
     * @return the updated notification response
     */
    public NotificationResponse markAsRead(UUID id, String userId) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Notification not found with ID: " + id));
        
        notification.setRead(true);
        Notification updatedNotification = notificationRepository.save(notification);
        
        return NotificationResponse.fromNotification(updatedNotification);
    }

    /**
     * Marks all notifications as read for a user.
     *
     * @param userId the user ID
     * @return the number of updated notifications
     */
    public int markAllAsRead(String userId) {
        int updatedCount = notificationRepository.updateAllReadByUserId(userId);
        return updatedCount;
    }

    /**
     * Deletes a notification.
     *
     * @param id the notification ID
     * @param userId the user ID
     */
    public void deleteNotification(UUID id, String userId) {
        notificationRepository.deleteByIdAndUserId(id, userId);
    }

    /**
     * Deletes all notifications for a user.
     *
     * @param userId the user ID
     */
    public void deleteAllByUserId(String userId) {
        notificationRepository.deleteAllByUserId(userId);
    }

    /**
     * Sends an email notification using the mail service.
     *
     * @param notification the notification to send
     */
    private void sendEmailNotification(Notification notification) {
        Map<String, Object> emailRequest = new HashMap<>();
        emailRequest.put("to", notification.getUserEmail());
        emailRequest.put("subject", notification.getTitle());
        emailRequest.put("content", notification.getContent());
        emailRequest.put("notificationId", notification.getId());
        
        ApiResponse<Map<String, Object>> response = mailServiceClient.sendEmail(emailRequest);
        
        if (!response.isSuccess()) {
            log.error("Mail service error: {}", response.getMessage());
            throw new ServiceException("Failed to send email notification: " + response.getMessage());
        }
        
        log.info("Email notification sent successfully to {}", notification.getUserEmail());
    }
} 