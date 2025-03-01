package com.goodjob.candidate.dto;

import com.goodjob.candidate.entity.CandidateCertification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for returning candidate certification data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateCertificationResponse {

    private Integer id;
    private String name;
    private String issuingOrganization;
    private String credentialId;
    private String credentialUrl;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private boolean doesNotExpire;
    private boolean valid;
    private String formattedValidity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Converts a CandidateCertification entity to a CandidateCertificationResponse DTO.
     *
     * @param certification the CandidateCertification entity
     * @return the CandidateCertificationResponse DTO
     */
    public static CandidateCertificationResponse fromEntity(CandidateCertification certification) {
        return CandidateCertificationResponse.builder()
                .id(certification.getId())
                .name(certification.getName())
                .issuingOrganization(certification.getIssuingOrganization())
                .credentialId(certification.getCredentialId())
                .credentialUrl(certification.getCredentialUrl())
                .issueDate(certification.getIssueDate())
                .expirationDate(certification.getExpirationDate())
                .doesNotExpire(certification.isDoesNotExpire())
                .valid(certification.isValid())
                .formattedValidity(certification.getFormattedValidity())
                .createdAt(certification.getCreatedAt())
                .updatedAt(certification.getUpdatedAt())
                .build();
    }
} 