package com.goodjob.company.infrastructure.feign.industry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for viewing industry data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndustryView {

    private Integer id;
    private String name;
    private LocalDateTime createdOn;
    private String createdBy;
    private LocalDateTime lastModifiedOn;
    private String lastModifiedBy;
    private boolean deleteFlg;
} 