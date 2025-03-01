package com.goodjob.job.query.dto;

import com.goodjob.job.entity.SalaryPeriod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for displaying job salary information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobSalaryView {
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    private String currency;
    private SalaryPeriod salaryPeriod;
    private Boolean isNegotiable;
    private Boolean isVisible;
} 