package com.goodjob.speciality.service;

import com.goodjob.speciality.dto.SpecialityDto;

import java.util.List;

/**
 * Service interface for querying specialities.
 * Following the CQRS pattern, this service handles all read operations.
 */
public interface SpecialityQueryService {

    /**
     * Get all specialities.
     *
     * @return a list of all specialities
     */
    List<SpecialityDto> getAllSpecialities();

    /**
     * Get a speciality by its ID.
     *
     * @param id the speciality ID
     * @return the speciality DTO
     */
    SpecialityDto getSpecialityById(Integer id);

    /**
     * Get a speciality by its name.
     *
     * @param name the speciality name
     * @return the speciality DTO
     */
    SpecialityDto getSpecialityByName(String name);
} 