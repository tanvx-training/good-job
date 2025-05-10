package com.goodjob.profile.domain.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for experience data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceDTO {
    
    private Long experienceId;
    private Long profileId;
    private String companyName;
    private Integer companyId;
    private String title;
    private String location;
    private Long startDate;
    private Long endDate;
    private Boolean current;
    private String description;
} 