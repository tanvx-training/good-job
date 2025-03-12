package com.goodjob.skill.query.dto;

import java.time.LocalDateTime;
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
    private LocalDateTime createdOn;
    private String createdBy;
    private LocalDateTime lastModifiedOn;
    private String lastModifiedBy;
    private boolean deleteFlg;
} 