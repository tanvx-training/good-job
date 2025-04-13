package com.goodjob.metadata.domain.industry.command;

import com.goodjob.metadata.domain.industry.dto.CreateIndustryCommand;
import com.goodjob.metadata.domain.industry.dto.UpdateIndustryCommand;

/**
 * Service interface for industry command operations.
 */
public interface IndustryCommandService {

    /**
     * Create a new industry.
     *
     * @param command the create industry command
     * @return the ID of the created industry
     */
    Integer createIndustry(CreateIndustryCommand command);

    /**
     * Update an existing industry.
     *
     * @param id the industry ID
     * @param command the update industry command
     */
    void updateIndustry(Integer id, UpdateIndustryCommand command);

    /**
     * Delete an industry.
     *
     * @param id the industry ID
     */
    void deleteIndustry(Integer id);
} 