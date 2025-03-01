package com.goodjob.job.command.dto;

import com.goodjob.job.entity.SkillLevel;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Command for creating a skill requirement for a job posting.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobSkillCommand {

    @NotNull(message = "Skill ID is required")
    @Positive(message = "Skill ID must be a positive number")
    private Integer skillId;

    private SkillLevel skillLevel;

    private Boolean isRequired;
} 