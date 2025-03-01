package com.goodjob.skill.command.service;

import com.goodjob.skill.command.dto.CreateSkillCommand;
import com.goodjob.skill.command.dto.UpdateSkillCommand;

/**
 * Service interface for command operations on skills.
 * Following the CQRS pattern, this service handles all write operations.
 */
public interface SkillCommandService {

    /**
     * Create a new skill.
     *
     * @param command the create skill command
     * @return the ID of the created skill
     */
    Integer createSkill(CreateSkillCommand command);

    /**
     * Update an existing skill.
     *
     * @param id the skill ID
     * @param command the update skill command
     */
    void updateSkill(Integer id, UpdateSkillCommand command);

    /**
     * Delete a skill by its ID.
     *
     * @param id the skill ID
     */
    void deleteSkill(Integer id);
} 