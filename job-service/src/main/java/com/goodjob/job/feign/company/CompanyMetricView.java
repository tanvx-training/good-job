package com.goodjob.job.feign.company;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CompanyMetricView {

    private Integer employeeCount;
    private Integer followerCount;
    private LocalDateTime recordOn;
}
