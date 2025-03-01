package com.goodjob.candidate.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Data Transfer Object for creating or updating a candidate.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateCommand {

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 100, message = "First name must be between 2 and 100 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
    private String lastName;

    @Size(max = 500, message = "Headline must be less than 500 characters")
    private String headline;

    @Size(max = 5000, message = "Summary must be less than 5000 characters")
    private String summary;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must be less than 100 characters")
    private String email;

    @Pattern(regexp = "^$|^\\+?[0-9\\s\\-\\(\\)]{8,20}$", message = "Phone number must be valid")
    private String phone;

    @Size(max = 100, message = "Location must be less than 100 characters")
    private String location;

    @Pattern(regexp = "^$|^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w .-]*)*/?$", message = "Profile picture URL must be valid")
    private String profilePictureUrl;

    @Pattern(regexp = "^$|^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w .-]*)*/?$", message = "Resume URL must be valid")
    private String resumeUrl;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @Size(max = 50, message = "Current job title must be less than 50 characters")
    private String currentJobTitle;

    @Size(max = 100, message = "Current company must be less than 100 characters")
    private String currentCompany;

    @Size(max = 100, message = "Highest education must be less than 100 characters")
    private String highestEducation;

    @Size(max = 100, message = "Education institution must be less than 100 characters")
    private String educationInstitution;

    @Pattern(regexp = "^$|^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w .-]*)*/?$", message = "LinkedIn URL must be valid")
    private String linkedinUrl;

    @Pattern(regexp = "^$|^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w .-]*)*/?$", message = "GitHub URL must be valid")
    private String githubUrl;

    @Pattern(regexp = "^$|^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w .-]*)*/?$", message = "Portfolio URL must be valid")
    private String portfolioUrl;

    private boolean openToWork;

    private boolean openToRelocate;

    private boolean profileVisible;
} 