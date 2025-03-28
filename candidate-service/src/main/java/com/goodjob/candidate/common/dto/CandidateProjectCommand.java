package com.goodjob.candidate.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

/**
 * Data Transfer Object for creating or updating a candidate project.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateProjectCommand {

    @NotBlank(message = "Project name is required")
    @Size(min = 2, max = 100, message = "Project name must be between 2 and 100 characters")
    private String name;

    @Size(max = 5000, message = "Description must be less than 5000 characters")
    private String description;

    @URL(message = "Project URL must be a valid URL")
    private String projectUrl;

    @URL(message = "Repository URL must be a valid URL")
    private String repositoryUrl;

    @NotNull(message = "Start date is required")
    @PastOrPresent(message = "Start date must be in the past or present")
    private LocalDate startDate;

    private LocalDate endDate;

    private boolean currentlyWorking;

    @Size(max = 500, message = "Technologies must be less than 500 characters")
    private String technologies;
} 