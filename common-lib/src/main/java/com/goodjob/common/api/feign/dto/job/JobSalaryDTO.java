package com.goodjob.common.api.feign.dto.job;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Job Salary information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobSalaryDTO implements Serializable {
    
    private Long id;
    private Long jobId;
    private Integer minSalary;
    private Integer maxSalary;
    private String currency;
    private Boolean isVisible;
} 