package com.goodjob.benefit.query.dto;

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
public class BenefitView {

    private Integer id;
    private String type;
} 