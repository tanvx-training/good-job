package com.goodjob.industry.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Industry entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndustryDto {

    private Integer id;

    @NotBlank(message = "Industry name is required")
    private String name;
} 