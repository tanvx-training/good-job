package com.goodjob.job.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for displaying job industry information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobIndustryView {
    private Integer industryId;
    private String industryName;
} 