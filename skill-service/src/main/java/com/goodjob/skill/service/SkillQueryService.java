package com.goodjob.skill.service;

import com.goodjob.skill.dto.SkillDto;

import java.util.List;

/**
 * Service interface for querying skills.
 * Following the CQRS pattern, this service handles all read operations.
 */
public interface SkillQueryService {

    /**
     * Get all skills.
     *
     * @return a list of all skills
     */
    List<SkillDto> getAllSkills();

    /**
     * Get a skill by its ID.
     *
     * @param id the skill ID
     * @return the skill DTO
     */
    SkillDto getSkillById(Integer id);

    /**
     * Get a skill by its abbreviation.
     *
     * @param abbreviation the skill abbreviation
     * @return the skill DTO
     */
    SkillDto getSkillByAbbreviation(String abbreviation);
} 