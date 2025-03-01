package com.goodjob.job.query.dto;

import com.goodjob.job.entity.EducationLevel;
import com.goodjob.job.entity.ExperienceLevel;
import com.goodjob.job.entity.JobType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

/**
 * DTO for job search criteria.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobSearchCriteria {
    private String keyword;
    private String location;
    private String companyName;
    private Set<JobType> jobTypes;
    private Set<ExperienceLevel> experienceLevels;
    private Set<EducationLevel> educationLevels;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    private String currency;
    private Set<Integer> skillIds;
    private Set<Integer> industryIds;
    private Boolean remote;
} 