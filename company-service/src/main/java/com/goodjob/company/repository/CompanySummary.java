package com.goodjob.company.repository;

import com.goodjob.company.query.dto.CompanyMetricView;

import java.util.ArrayList;
import java.util.List;

public interface CompanySummary {

    Integer getCompanyId();
    String getName();
    String getDescription();
    Integer getCompanySize();
    String getState();
    String getCountry();
    String getCity();
    String getZipCode();
    String getAddress();
    String getUrl();
    CompanyMetricSummary getCompanyMetric();
    List<CompanyIndustrySummary> getCompanyIndustries();
    List<CompanySpecialitySummary> getCompanySpecialities();
}
