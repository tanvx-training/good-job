package com.goodjob.job.command.dto;

import com.goodjob.job.entity.SalaryPeriod;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Command for updating salary information for a job posting.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateJobSalaryCommand {

    @DecimalMin(value = "0.0", message = "Minimum salary must be a positive number")
    private BigDecimal minSalary;

    @DecimalMin(value = "0.0", message = "Maximum salary must be a positive number")
    private BigDecimal maxSalary;

    @Size(min = 3, max = 3, message = "Currency must be a 3-letter code (e.g., USD)")
    private String currency;

    private SalaryPeriod salaryPeriod;

    private Boolean isNegotiable;

    private Boolean isVisible;
} 