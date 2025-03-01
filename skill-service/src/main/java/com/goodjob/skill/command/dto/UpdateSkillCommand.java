package com.goodjob.skill.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Command for updating an existing skill.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSkillCommand {

    @NotBlank(message = "Skill abbreviation is required")
    @Size(min = 1, max = 10, message = "Skill abbreviation must be between 1 and 10 characters")
    private String abbreviation;

    @NotBlank(message = "Skill name is required")
    @Size(min = 1, max = 50, message = "Skill name must be between 1 and 50 characters")
    private String name;
} 