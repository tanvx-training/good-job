package com.goodjob.candidate.dto;

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
 * Data Transfer Object for creating or updating a candidate education.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateEducationCommand {

    @NotBlank(message = "Institution name is required")
    @Size(min = 2, max = 100, message = "Institution name must be between 2 and 100 characters")
    private String institution;

    @NotBlank(message = "Degree is required")
    @Size(min = 2, max = 100, message = "Degree must be between 2 and 100 characters")
    private String degree;

    @Size(min = 2, max = 100, message = "Field of study must be between 2 and 100 characters")
    private String fieldOfStudy;

    @Size(max = 100, message = "Location must be less than 100 characters")
    private String location;

    @NotNull(message = "Start date is required")
    @PastOrPresent(message = "Start date must be in the past or present")
    private LocalDate startDate;

    private LocalDate endDate;

    private boolean currentlyEnrolled;

    private Double grade;

    @Size(max = 100, message = "Grade scale must be less than 100 characters")
    private String gradeScale;

    @Size(max = 5000, message = "Activities and societies must be less than 5000 characters")
    private String activitiesAndSocieties;

    @Size(max = 5000, message = "Description must be less than 5000 characters")
    private String description;
} 