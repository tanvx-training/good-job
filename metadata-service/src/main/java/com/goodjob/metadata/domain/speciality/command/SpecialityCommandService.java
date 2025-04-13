package com.goodjob.metadata.domain.speciality.command;

import com.goodjob.metadata.domain.speciality.dto.CreateSpecialityCommand;
import com.goodjob.metadata.domain.speciality.dto.UpdateSpecialityCommand;

/**
 * Service interface for command operations on specialities.
 * Following the CQRS pattern, this service handles all write operations.
 */
public interface SpecialityCommandService {

    /**
     * Create a new speciality.
     *
     * @param command the create speciality command
     * @return the ID of the created speciality
     */
    Integer createSpeciality(CreateSpecialityCommand command);

    /**
     * Update an existing speciality.
     *
     * @param id the speciality ID
     * @param command the update speciality command
     */
    void updateSpeciality(Integer id, UpdateSpecialityCommand command);

    /**
     * Delete a speciality by its ID.
     *
     * @param id the speciality ID
     */
    void deleteSpeciality(Integer id);
} 