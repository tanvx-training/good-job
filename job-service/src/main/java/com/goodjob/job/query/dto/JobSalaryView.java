package com.goodjob.job.query.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class JobSalaryView {

    private BigDecimal minSalary;
    private BigDecimal medSalary;
    private BigDecimal maxSalary;
    private String salaryPeriod;
    private String currency;
    private String compensationType;
} 