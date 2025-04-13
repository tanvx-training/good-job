package com.goodjob.company.domain.company.repository;

import java.time.LocalDateTime;

public interface CompanyMetricSummary {

    Integer getEmployeeCount();
    Integer getFollowerCount();
    Long getRecordOn();
}
