package com.goodjob.common.api.feign.dto.job;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Job Industry information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobIndustryDTO implements Serializable {
    
    private Long id;
    private Long jobId;
    private Integer industryId;
    private String industryName;
} 