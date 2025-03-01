package com.goodjob.benefit.command.service;

import com.goodjob.benefit.command.dto.CreateBenefitCommand;
import com.goodjob.benefit.command.dto.UpdateBenefitCommand;

/**
 * Service interface for benefit command operations.
 */
public interface BenefitCommandService {

    /**
     * Create a new benefit.
     *
     * @param command the create benefit command
     * @return the ID of the created benefit
     */
    Integer createBenefit(CreateBenefitCommand command);

    /**
     * Update an existing benefit.
     *
     * @param id the benefit ID
     * @param command the update benefit command
     */
    void updateBenefit(Integer id, UpdateBenefitCommand command);

    /**
     * Delete a benefit.
     *
     * @param id the benefit ID
     */
    void deleteBenefit(Integer id);
} 