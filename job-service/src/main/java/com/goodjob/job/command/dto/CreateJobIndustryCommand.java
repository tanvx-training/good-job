package com.goodjob.job.command.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Command for creating an industry association for a job posting.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobIndustryCommand {

    @NotNull(message = "Industry ID is required")
    @Positive(message = "Industry ID must be a positive number")
    private Integer industryId;
} 