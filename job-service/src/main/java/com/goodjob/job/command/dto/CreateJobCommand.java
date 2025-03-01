package com.goodjob.job.command.dto;

import com.goodjob.job.entity.EducationLevel;
import com.goodjob.job.entity.ExperienceLevel;
import com.goodjob.job.entity.JobType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Command for creating a new job posting.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobCommand {

    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 20, message = "Description must be at least 20 characters")
    private String description;

    @Size(min = 20, message = "Requirements must be at least 20 characters")
    private String requirements;

    @NotBlank(message = "Company name is required")
    @Size(min = 2, max = 100, message = "Company name must be between 2 and 100 characters")
    private String companyName;

    @NotBlank(message = "Location is required")
    @Size(min = 2, max = 100, message = "Location must be between 2 and 100 characters")
    private String location;

    @NotNull(message = "Job type is required")
    private JobType jobType;

    private ExperienceLevel experienceLevel;

    private EducationLevel educationLevel;

    @Future(message = "Expiration date must be in the future")
    private LocalDateTime expiresAt;

    @Valid
    private CreateJobSalaryCommand salary;

    @Valid
    @Size(min = 1, message = "At least one skill is required")
    private Set<CreateJobSkillCommand> skills;

    @Valid
    @Size(min = 1, message = "At least one industry is required")
    private Set<CreateJobIndustryCommand> industries;
} 