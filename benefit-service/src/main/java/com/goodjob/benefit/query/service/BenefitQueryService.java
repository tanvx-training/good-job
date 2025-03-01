package com.goodjob.benefit.query.service;

import com.goodjob.benefit.query.dto.BenefitView;

import java.util.List;

/**
 * Service interface for benefit query operations.
 */
public interface BenefitQueryService {

    /**
     * Get all benefits.
     *
     * @return list of all benefits
     */
    List<BenefitView> getAllBenefits();

    /**
     * Get a benefit by ID.
     *
     * @param id the benefit ID
     * @return the benefit
     */
    BenefitView getBenefitById(Integer id);
} 