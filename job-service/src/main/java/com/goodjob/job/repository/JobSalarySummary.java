package com.goodjob.job.repository;

import java.math.BigDecimal;

public interface JobSalarySummary {

  BigDecimal getMinSalary();
  BigDecimal getMedSalary();
  BigDecimal getMaxSalary();
  Integer getPayPeriod();
  String getCurrency();
  Integer getCompensationType();
}
