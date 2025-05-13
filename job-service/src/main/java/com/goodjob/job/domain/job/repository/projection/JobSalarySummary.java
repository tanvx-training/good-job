package com.goodjob.job.domain.job.repository.projection;

import java.math.BigDecimal;

public interface JobSalarySummary {

  BigDecimal getMinSalary();
  BigDecimal getMedSalary();
  BigDecimal getMaxSalary();
  Integer getPayPeriod();
  String getCurrency();
  Integer getCompensationType();
}
