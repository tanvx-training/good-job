package com.goodjob.benefit.service;

import com.goodjob.benefit.dto.BenefitDto;

import java.util.List;

/**
 * Service interface for managing benefits.
 */
public interface BenefitService {

    /**
     * Create a new benefit.
     *
     * @param benefitDto the benefit data
     * @return the created benefit
     */
    BenefitDto createBenefit(BenefitDto benefitDto);

    /**
     * Get all benefits.
     *
     * @return list of all benefits
     */
    List<BenefitDto> getAllBenefits();

    /**
     * Get all active benefits.
     *
     * @return list of active benefits
     */
    List<BenefitDto> getActiveBenefits();

    /**
     * Get a benefit by ID.
     *
     * @param id the benefit ID
     * @return the benefit
     */
    BenefitDto getBenefitById(Long id);

    /**
     * Update a benefit.
     *
     * @param id the benefit ID
     * @param benefitDto the updated benefit data
     * @return the updated benefit
     */
    BenefitDto updateBenefit(Long id, BenefitDto benefitDto);

    /**
     * Delete a benefit.
     *
     * @param id the benefit ID
     */
    void deleteBenefit(Long id);
} 