package com.goodjob.metadata.query.service;

import com.goodjob.common.dto.response.PageResponseDTO;
import com.goodjob.metadata.query.dto.SpecialityQuery;
import com.goodjob.metadata.query.dto.SpecialityView;

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
    PageResponseDTO<SpecialityView> getAllSpecialities(SpecialityQuery query);

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

    List<SpecialityView> getAllByIdList(List<Integer> idList);
} 