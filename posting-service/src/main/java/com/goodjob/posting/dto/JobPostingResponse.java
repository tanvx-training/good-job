package com.goodjob.posting.dto;

import com.goodjob.posting.entity.EducationLevel;
import com.goodjob.posting.entity.ExperienceLevel;
import com.goodjob.posting.entity.JobType;
import com.goodjob.posting.entity.PostingStatus;
import com.goodjob.posting.entity.SalaryPeriod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for returning job posting information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobPostingResponse {

    private Integer id;
    private Integer jobId;
    private String employerId;
    private String title;
    private String description;
    private String companyName;
    private String location;
    private JobType jobType;
    private ExperienceLevel experienceLevel;
    private EducationLevel educationLevel;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    private String currency;
    private SalaryPeriod salaryPeriod;
    private PostingStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime expiresAt;
    private Integer views;
    private Integer applicationCount;
} 