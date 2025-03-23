package com.goodjob.job.feign.company;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
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
