package com.goodjob.notification.controller;

import com.goodjob.notification.dto.NotificationCountResponse;
import com.goodjob.notification.dto.NotificationRequest;
import com.goodjob.notification.dto.NotificationResponse;
import com.goodjob.notification.kafka.NotificationProducer;
import com.goodjob.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for notification operations.
 */
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationProducer notificationProducer;

    /**
     * Creates a new notification.
     *
     * @param request the notification request
     * @return the created notification
     */
    @PostMapping
    public ResponseEntity<Void> createNotification(@Valid @RequestBody NotificationRequest request) {
        log.info("Received request to create notification: {}", request);
        notificationProducer.sendNotification(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
     * Gets all notifications for a user.
     *
     * @param userId the user ID
     * @return the list of notifications
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponse>> getUserNotifications(@PathVariable String userId) {
        log.info("Fetching notifications for user: {}", userId);
        List<NotificationResponse> notifications = notificationService.getUserNotifications(userId);
        return ResponseEntity.ok(notifications);
    }

    /**
     * Gets the count of unread notifications for a user.
     *
     * @param userId the user ID
     * @return the unread count
     */
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<NotificationCountResponse> getUnreadCount(@PathVariable String userId) {
        log.info("Fetching unread notification count for user: {}", userId);
        NotificationCountResponse countResponse = notificationService.getUnreadCount(userId);
        return ResponseEntity.ok(countResponse);
    }

    /**
     * Marks a notification as read.
     *
     * @param notificationId the notification ID
     * @param userId the user ID
     * @return the updated notification
     */
    @PutMapping("/{notificationId}/read")
    public ResponseEntity<NotificationResponse> markAsRead(
            @PathVariable String notificationId,
            @RequestParam String userId) {
        log.info("Marking notification {} as read for user: {}", notificationId, userId);
        NotificationResponse notification = notificationService.markAsRead(notificationId, userId);
        return ResponseEntity.ok(notification);
    }

    /**
     * Marks all notifications as read for a user.
     *
     * @param userId the user ID
     * @return the response entity
     */
    @PutMapping("/user/{userId}/read-all")
    public ResponseEntity<Void> markAllAsRead(@PathVariable String userId) {
        log.info("Marking all notifications as read for user: {}", userId);
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes a notification.
     *
     * @param notificationId the notification ID
     * @param userId the user ID
     * @return the response entity
     */
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(
            @PathVariable String notificationId,
            @RequestParam String userId) {
        log.info("Deleting notification {} for user: {}", notificationId, userId);
        notificationService.deleteNotification(notificationId, userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes all notifications for a user.
     *
     * @param userId the user ID
     * @return the response entity
     */
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteAllNotifications(@PathVariable String userId) {
        log.info("Deleting all notifications for user: {}", userId);
        notificationService.deleteAllNotifications(userId);
        return ResponseEntity.noContent().build();
    }
} 