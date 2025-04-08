package com.goodjob.metadata.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for viewing speciality information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialityView {

    private Integer id;
    private String name;
    private LocalDateTime createdOn;
    private String createdBy;
    private LocalDateTime lastModifiedOn;
    private String lastModifiedBy;
    private boolean deleteFlg;
} 