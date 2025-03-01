package com.goodjob.industry.command.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Command for creating a new industry.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateIndustryCommand {

    @NotBlank(message = "Industry name is required")
    private String name;
} 