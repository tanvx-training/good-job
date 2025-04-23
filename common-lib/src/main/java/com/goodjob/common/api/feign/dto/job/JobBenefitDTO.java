package com.goodjob.common.api.feign.dto.job;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Job Benefit information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobBenefitDTO implements Serializable {
    
    private Long id;
    private Long jobId;
    private Integer benefitId;
    private String benefitName;
} 