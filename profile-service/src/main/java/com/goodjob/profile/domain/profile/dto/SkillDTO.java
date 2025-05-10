package com.goodjob.profile.domain.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for skill data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillDTO {
    
    private Long skillId;
    private Long profileSkillId;
    private String name;
    private String description;
    private Integer proficiencyLevel;
} 