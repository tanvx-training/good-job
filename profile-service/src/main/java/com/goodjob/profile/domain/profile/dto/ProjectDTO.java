package com.goodjob.profile.domain.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for project data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    
    private Long projectId;
    private Long profileId;
    private String name;
    private String description;
    private Long startDate;
    private Long endDate;
    private Boolean current;
    private String url;
} 