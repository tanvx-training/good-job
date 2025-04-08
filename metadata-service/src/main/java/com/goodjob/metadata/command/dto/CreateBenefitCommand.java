package com.goodjob.metadata.command.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Command for creating a new benefit.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBenefitCommand {

    @NotBlank(message = "Benefit type is required")
    private String type;
} 