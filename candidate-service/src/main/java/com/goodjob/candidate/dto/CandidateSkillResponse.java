package com.goodjob.candidate.dto;

import com.goodjob.candidate.entity.CandidateSkill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for returning candidate skill data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateSkillResponse {

    private Integer id;
    private String skillName;
    private String proficiencyLevel;
    private Integer yearsOfExperience;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Converts a CandidateSkill entity to a CandidateSkillResponse DTO.
     *
     * @param skill the CandidateSkill entity
     * @return the CandidateSkillResponse DTO
     */
    public static CandidateSkillResponse fromEntity(CandidateSkill skill) {
        return CandidateSkillResponse.builder()
                .id(skill.getId())
                .skillName(skill.getSkillName())
                .proficiencyLevel(skill.getProficiencyLevel().getDisplayName())
                .yearsOfExperience(skill.getYearsOfExperience())
                .description(skill.getDescription())
                .createdAt(skill.getCreatedAt())
                .updatedAt(skill.getUpdatedAt())
                .build();
    }
} 