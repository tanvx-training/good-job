package com.goodjob.posting.dto;

import com.goodjob.posting.entity.EducationLevel;
import com.goodjob.posting.entity.ExperienceLevel;
import com.goodjob.posting.entity.JobType;
import com.goodjob.posting.entity.SalaryPeriod;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for creating or updating a job posting.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobPostingRequest {

    @NotNull(message = "Job ID is required")
    private Integer jobId;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Job type is required")
    private JobType jobType;

    private ExperienceLevel experienceLevel;

    private EducationLevel educationLevel;

    @DecimalMin(value = "0.0", message = "Minimum salary must be non-negative")
    private BigDecimal minSalary;

    @DecimalMin(value = "0.0", message = "Maximum salary must be non-negative")
    private BigDecimal maxSalary;

    private String currency;

    private SalaryPeriod salaryPeriod;

    @Future(message = "Expiration date must be in the future")
    private LocalDateTime expiresAt;
} 