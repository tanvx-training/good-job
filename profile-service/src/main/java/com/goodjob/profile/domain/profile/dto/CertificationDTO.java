package com.goodjob.profile.domain.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for certification data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificationDTO {
    
    private Long certificationId;
    private Long profileId;
    private String name;
    private String organization;
    private Long issueDate;
    private Long expirationDate;
    private String credentialId;
    private String credentialUrl;
} 