package com.goodjob.company.domain.company.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CompanyView {

    private Integer companyId;
    private String name;
    private String description;
    private String companySize;
    private String address;
    private String url;
    private CompanyMetricView metric;
    private Set<CompanyIndustryView> industries;
    private Set<CompanySpecialityView> specialities;
}
