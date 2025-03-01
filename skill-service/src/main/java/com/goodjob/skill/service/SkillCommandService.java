package com.goodjob.skill.service;

import com.goodjob.skill.dto.SkillDto;

/**
 * Service interface for command operations on skills.
 * Following the CQRS pattern, this service handles all write operations.
 */
public interface SkillCommandService {

    /**
     * Create a new skill.
     *
     * @param skillDto the skill DTO
     * @return the created skill DTO
     */
    SkillDto createSkill(SkillDto skillDto);

    /**
     * Update an existing skill.
     *
     * @param id the skill ID
     * @param skillDto the skill DTO with updated information
     * @return the updated skill DTO
     */
    SkillDto updateSkill(Integer id, SkillDto skillDto);

    /**
     * Delete a skill by its ID.
     *
     * @param id the skill ID
     */
    void deleteSkill(Integer id);
} 