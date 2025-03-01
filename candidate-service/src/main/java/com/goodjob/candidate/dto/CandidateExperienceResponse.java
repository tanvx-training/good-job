package com.goodjob.candidate.dto;

import com.goodjob.candidate.entity.CandidateExperience;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for returning candidate experience data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateExperienceResponse {

    private Integer id;
    private String jobTitle;
    private String company;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean currentlyWorking;
    private String description;
    private String formattedDuration;
    private int durationInMonths;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Converts a CandidateExperience entity to a CandidateExperienceResponse DTO.
     *
     * @param experience the CandidateExperience entity
     * @return the CandidateExperienceResponse DTO
     */
    public static CandidateExperienceResponse fromEntity(CandidateExperience experience) {
        return CandidateExperienceResponse.builder()
                .id(experience.getId())
                .jobTitle(experience.getJobTitle())
                .company(experience.getCompany())
                .location(experience.getLocation())
                .startDate(experience.getStartDate())
                .endDate(experience.getEndDate())
                .currentlyWorking(experience.isCurrentlyWorking())
                .description(experience.getDescription())
                .formattedDuration(experience.getFormattedDuration())
                .durationInMonths(experience.getDurationInMonths())
                .createdAt(experience.getCreatedAt())
                .updatedAt(experience.getUpdatedAt())
                .build();
    }
} 