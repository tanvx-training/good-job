package com.goodjob.company.query.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CompanyView {

    private String companyId;
    private String name;
    private String description;
    private String companySize;
    private String address;
    private String url;
    private CompanyMetricView metric;
    private List<CompanyIndustryView> industries;
    private List<CompanySpecialityView> specialities;
}
