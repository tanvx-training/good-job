package com.goodjob.speciality.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Speciality entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialityDto {

    private Integer id;

    @NotBlank(message = "Speciality name is required")
    @Size(min = 2, max = 100, message = "Speciality name must be between 2 and 100 characters")
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
} 