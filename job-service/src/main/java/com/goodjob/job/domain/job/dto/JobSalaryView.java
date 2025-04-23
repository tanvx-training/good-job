package com.goodjob.job.domain.job.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for displaying job salary information.
 */
@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class JobSalaryView implements Serializable {

    private BigDecimal minSalary;
    private BigDecimal medSalary;
    private BigDecimal maxSalary;
    private String salaryPeriod;
    private String currency;
    private String compensationType;
} 