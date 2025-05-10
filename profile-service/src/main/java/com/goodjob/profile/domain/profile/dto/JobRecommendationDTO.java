package com.goodjob.profile.domain.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for job recommendation data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobRecommendationDTO {
    
    private Long jobId;
    private String title;
    private String companyName;
    private Integer companyId;
    private String location;
    private String description;
    private Double matchScore;
    private Integer workType;
    private Boolean remoteAllowed;
    private Long expiry;
} 