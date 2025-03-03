package com.goodjob.candidate.client;

import com.goodjob.candidate.config.FeignConfig;
import com.goodjob.common.dto.ApiResponse;
import com.goodjob.common.feign.NotificationServiceClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * Feign client for the notification service.
 * Extends the common notification service client and adds service-specific methods.
 */
@FeignClient(
    name = "notification-service",
    configuration = FeignConfig.class,
    fallback = NotificationClient.NotificationClientFallback.class
)
public interface NotificationClient extends NotificationServiceClient {

    /**
     * Fallback implementation for the notification client.
     * Provides fallback behavior when the notification service is unavailable.
     */
    @Component
    class NotificationClientFallback extends com.goodjob.common.feign.NotificationServiceClientFallback
            implements NotificationClient {
        // Add any candidate-service specific fallback methods here
    }
} 