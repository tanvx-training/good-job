package com.goodjob.candidate.dto;

import com.goodjob.candidate.entity.CandidateProject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for returning candidate project data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateProjectResponse {

    private Integer id;
    private String name;
    private String description;
    private String projectUrl;
    private String repositoryUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean currentlyWorking;
    private String technologies;
    private String formattedPeriod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Converts a CandidateProject entity to a CandidateProjectResponse DTO.
     *
     * @param project the CandidateProject entity
     * @return the CandidateProjectResponse DTO
     */
    public static CandidateProjectResponse fromEntity(CandidateProject project) {
        return CandidateProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .projectUrl(project.getProjectUrl())
                .repositoryUrl(project.getRepositoryUrl())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .currentlyWorking(project.isCurrentlyWorking())
                .technologies(project.getTechnologies())
                .formattedPeriod(project.getFormattedPeriod())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }
} 