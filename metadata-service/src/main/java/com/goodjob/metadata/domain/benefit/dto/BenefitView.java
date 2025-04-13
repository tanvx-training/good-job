package com.goodjob.metadata.domain.benefit.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for viewing benefit data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BenefitView {

    private Integer id;
    private String type;
    private LocalDateTime createdOn;
    private String createdBy;
    private LocalDateTime lastModifiedOn;
    private String lastModifiedBy;
    private boolean deleteFlg;
} 