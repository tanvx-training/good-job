package com.goodjob.metadata.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Command for creating a new speciality.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSpecialityCommand {

    @NotBlank(message = "Speciality name is required")
    @Size(min = 2, max = 100, message = "Speciality name must be between 2 and 100 characters")
    private String name;
} 