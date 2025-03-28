package com.goodjob.candidate.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Data Transfer Object for creating or updating a candidate experience.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateExperienceCommand {

    @NotBlank(message = "Job title is required")
    @Size(min = 2, max = 100, message = "Job title must be between 2 and 100 characters")
    private String jobTitle;

    @NotBlank(message = "Company is required")
    @Size(min = 2, max = 100, message = "Company must be between 2 and 100 characters")
    private String company;

    @Size(max = 100, message = "Location must be less than 100 characters")
    private String location;

    @NotNull(message = "Start date is required")
    @PastOrPresent(message = "Start date must be in the past or present")
    private LocalDate startDate;

    private LocalDate endDate;

    private boolean currentlyWorking;

    @Size(max = 5000, message = "Description must be less than 5000 characters")
    private String description;
} 