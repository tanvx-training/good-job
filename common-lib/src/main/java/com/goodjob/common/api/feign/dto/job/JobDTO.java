package com.goodjob.common.api.feign.dto.job;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Job information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO implements Serializable {
    
    private Long jobId;
    private Integer companyId;
    private String title;
    private String description;
    private Integer workType;
    private Integer educationLevel;
    private Integer experienceLevel;
    private Boolean remoteAllowed;
    private String location;
    private String zipCode;
    private String skillsDesc;
    private Long expiry;
    private Long closedTime;
    private Integer jobStatus;
    private Integer views;
    private Integer applies;
    
    private JobSalaryDTO salary;
    private Set<JobBenefitDTO> benefits;
    private Set<JobSkillDTO> skills;
    private Set<JobIndustryDTO> industries;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 