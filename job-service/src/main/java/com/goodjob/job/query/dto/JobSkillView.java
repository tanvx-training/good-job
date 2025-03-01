package com.goodjob.job.query.dto;

import com.goodjob.job.entity.SkillLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for displaying job skill information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobSkillView {
    private Integer skillId;
    private String skillName;
    private String skillAbr;
    private SkillLevel skillLevel;
    private Boolean isRequired;
} 