package com.goodjob.speciality.service;

import com.goodjob.speciality.dto.SpecialityDto;

/**
 * Service interface for command operations on specialities.
 * Following the CQRS pattern, this service handles all write operations.
 */
public interface SpecialityCommandService {

    /**
     * Create a new speciality.
     *
     * @param specialityDto the speciality DTO
     * @return the created speciality DTO
     */
    SpecialityDto createSpeciality(SpecialityDto specialityDto);

    /**
     * Update an existing speciality.
     *
     * @param id the speciality ID
     * @param specialityDto the speciality DTO with updated information
     * @return the updated speciality DTO
     */
    SpecialityDto updateSpeciality(Integer id, SpecialityDto specialityDto);

    /**
     * Delete a speciality by its ID.
     *
     * @param id the speciality ID
     */
    void deleteSpeciality(Integer id);
} 