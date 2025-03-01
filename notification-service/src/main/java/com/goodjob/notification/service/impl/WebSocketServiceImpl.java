package com.goodjob.notification.service.impl;

import com.goodjob.notification.dto.NotificationCountResponse;
import com.goodjob.notification.dto.NotificationResponse;
import com.goodjob.notification.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Implementation of the WebSocketService interface.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketServiceImpl implements WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    private static final String NOTIFICATION_DESTINATION = "/topic/notifications/";
    private static final String COUNT_DESTINATION = "/topic/notifications/count/";

    /**
     * Sends a notification to a user via WebSocket.
     *
     * @param notification the notification response
     */
    @Override
    public void sendNotification(NotificationResponse notification) {
        String destination = NOTIFICATION_DESTINATION + notification.getUserId();
        log.info("Sending notification to destination: {}", destination);
        messagingTemplate.convertAndSend(destination, notification);
    }

    /**
     * Sends the unread notification count to a user via WebSocket.
     *
     * @param countResponse the notification count response
     */
    @Override
    public void sendUnreadCount(NotificationCountResponse countResponse) {
        String destination = COUNT_DESTINATION + countResponse.getUserId();
        log.info("Sending unread count to destination: {}", destination);
        messagingTemplate.convertAndSend(destination, countResponse);
    }
} 