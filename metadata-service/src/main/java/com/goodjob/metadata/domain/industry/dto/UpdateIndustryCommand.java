package com.goodjob.metadata.domain.industry.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Command for updating an existing industry.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateIndustryCommand {

    @NotBlank(message = "Industry name is required")
    @Size(min = 2, max = 265, message = "Industry name must be between 2 and 265 characters")
    private String name;
} 