package com.goodjob.company.query.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CompanyMetricView {

    private Integer id;
    private Integer employeeCount;
    private Integer followerCount;
    private LocalDateTime recordOn;
}
