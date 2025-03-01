package com.goodjob.skill.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for viewing skill information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillView {

    private Integer id;
    private String abbreviation;
    private String name;
} 