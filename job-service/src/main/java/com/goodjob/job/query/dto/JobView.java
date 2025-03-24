package com.goodjob.job.query.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

/**
 * DTO for displaying job information.
 */
@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class JobView {

    private Long jobId;
    private JobCompanyView company;
    private String title;
    private String description;
    private String workType;
    private String educationLevel;
    private String experienceLevel;
    private Boolean remoteAllowed;
    private String location;
    private String zipCode;
    private String skillsDesc;
    private LocalDateTime expiry;
    private LocalDateTime closedTime;
    private String jobStatus;
    private JobSalaryView salary;
    private Set<JobBenefitView> benefits;
    private Set<JobSkillView> skills;
    private Set<JobIndustryView> industries;
} 