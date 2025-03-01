package com.goodjob.industry.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
} 