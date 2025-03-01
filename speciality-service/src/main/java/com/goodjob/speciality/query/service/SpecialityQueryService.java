package com.goodjob.speciality.query.service;

import com.goodjob.speciality.query.dto.SpecialityView;

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
    List<SpecialityView> getAllSpecialities();

    /**
     * Get a speciality by its ID.
     *
     * @param id the speciality ID
     * @return the speciality view
     */
    SpecialityView getSpecialityById(Integer id);

    /**
     * Get a speciality by its name.
     *
     * @param name the speciality name
     * @return the speciality view
     */
    SpecialityView getSpecialityByName(String name);
} 