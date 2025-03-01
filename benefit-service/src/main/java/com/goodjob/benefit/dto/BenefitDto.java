package com.goodjob.benefit.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Benefit entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BenefitDto {

    private Integer id;

    @NotBlank(message = "Benefit type is required")
    private String type;
} 