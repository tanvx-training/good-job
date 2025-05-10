package com.goodjob.profile.domain.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for education data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationDTO {
    
    private Long educationId;
    private Long profileId;
    private String schoolName;
    private String degree;
    private String fieldOfStudy;
    private Long startDate;
    private Long endDate;
    private Boolean current;
    private String description;
} 