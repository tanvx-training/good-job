package com.goodjob.job.command.dto;

import com.goodjob.job.entity.EducationLevel;
import com.goodjob.job.entity.ExperienceLevel;
import com.goodjob.job.entity.JobStatus;
import com.goodjob.job.entity.JobType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Command for updating an existing job posting.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateJobCommand {

    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    private String title;

    @Size(min = 20, message = "Description must be at least 20 characters")
    private String description;

    @Size(min = 20, message = "Requirements must be at least 20 characters")
    private String requirements;

    @Size(min = 2, max = 100, message = "Company name must be between 2 and 100 characters")
    private String companyName;

    @Size(min = 2, max = 100, message = "Location must be between 2 and 100 characters")
    private String location;

    private JobType jobType;

    private ExperienceLevel experienceLevel;

    private EducationLevel educationLevel;

    private JobStatus status;

    @Future(message = "Expiration date must be in the future")
    private LocalDateTime expiresAt;

    @Valid
    private UpdateJobSalaryCommand salary;

    @Valid
    private Set<CreateJobSkillCommand> skills;

    @Valid
    private Set<CreateJobIndustryCommand> industries;
} 