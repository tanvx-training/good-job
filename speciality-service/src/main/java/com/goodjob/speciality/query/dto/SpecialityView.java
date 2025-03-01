package com.goodjob.speciality.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String description;
} 