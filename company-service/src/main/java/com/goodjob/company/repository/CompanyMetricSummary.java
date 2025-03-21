package com.goodjob.company.repository;

import java.time.LocalDateTime;

public interface CompanyMetricSummary {

    Integer getEmployeeCount();
    Integer getFollowerCount();
    Long getRecordOn();
}
