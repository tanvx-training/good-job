package com.goodjob.candidate.dto;

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
 * Data Transfer Object for creating or updating a candidate certification.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateCertificationCommand {

    @NotBlank(message = "Certification name is required")
    @Size(min = 2, max = 100, message = "Certification name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Issuing organization is required")
    @Size(min = 2, max = 100, message = "Issuing organization must be between 2 and 100 characters")
    private String issuingOrganization;

    @Size(max = 100, message = "Credential ID must be less than 100 characters")
    private String credentialId;

    @URL(message = "Credential URL must be a valid URL")
    private String credentialUrl;

    @NotNull(message = "Issue date is required")
    @PastOrPresent(message = "Issue date must be in the past or present")
    private LocalDate issueDate;

    private LocalDate expirationDate;

    private boolean doesNotExpire;
} 