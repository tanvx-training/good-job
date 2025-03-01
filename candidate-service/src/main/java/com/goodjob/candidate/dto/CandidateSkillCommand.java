package com.goodjob.candidate.dto;

import com.goodjob.candidate.entity.CandidateSkill.ProficiencyLevel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for creating or updating a candidate skill.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateSkillCommand {

    @NotBlank(message = "Skill name is required")
    @Size(min = 2, max = 100, message = "Skill name must be between 2 and 100 characters")
    private String skillName;

    private ProficiencyLevel proficiencyLevel;

    @Min(value = 0, message = "Years of experience must be at least 0")
    @Max(value = 50, message = "Years of experience must be at most 50")
    private Integer yearsOfExperience;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;
} 