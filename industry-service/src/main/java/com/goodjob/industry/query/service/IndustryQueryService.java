package com.goodjob.industry.query.service;

import com.goodjob.industry.query.dto.IndustryView;

import java.util.List;

/**
 * Service interface for industry query operations.
 */
public interface IndustryQueryService {

    /**
     * Get all industries.
     *
     * @return list of all industries
     */
    List<IndustryView> getAllIndustries();

    /**
     * Get an industry by ID.
     *
     * @param id the industry ID
     * @return the industry
     */
    IndustryView getIndustryById(Integer id);
} 