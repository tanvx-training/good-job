package com.goodjob.common.api.feign.dto.company;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<CompanyIndustryView> industries;
    private List<CompanySpecialityView> specialities;
}
