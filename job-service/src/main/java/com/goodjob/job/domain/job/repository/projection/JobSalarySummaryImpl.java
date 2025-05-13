package com.goodjob.job.domain.job.repository.projection;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class JobSalarySummaryImpl implements JobSalarySummary {

    private Integer salaryId;
    private BigDecimal minSalary;
    private BigDecimal medSalary;
    private BigDecimal maxSalary;
    private Integer payPeriod;
    private String currency;
    private Integer compensationType;
}
