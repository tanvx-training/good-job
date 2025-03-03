package com.goodjob.notification.client;

import com.goodjob.common.config.FeignClientConfig;
import com.goodjob.common.dto.ApiResponse;
import com.goodjob.common.feign.BaseFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * Feign client for the company service.
 * Provides methods to interact with the company service.
 */
@FeignClient(
    name = "company-service",
    configuration = FeignClientConfig.class,
    fallback = CompanyServiceClientFallback.class
)
public interface CompanyServiceClient extends BaseFeignClient {

    /**
     * Gets a company by ID.
     *
     * @param companyId the company ID
     * @return the company details
     */
    @GetMapping("/api/companies/{companyId}")
    ApiResponse<Map<String, Object>> getCompany(@PathVariable Long companyId);

    /**
     * Gets a company's notification settings.
     *
     * @param companyId the company ID
     * @return the notification settings
     */
    @GetMapping("/api/companies/{companyId}/notification-settings")
    ApiResponse<Map<String, Object>> getCompanyNotificationSettings(@PathVariable Long companyId);
} 