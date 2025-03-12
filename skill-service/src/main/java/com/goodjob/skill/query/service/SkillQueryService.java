package com.goodjob.skill.query.service;

import com.goodjob.common.dto.PageResponseDTO;
import com.goodjob.skill.query.dto.SkillQuery;
import com.goodjob.skill.query.dto.SkillView;

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
    PageResponseDTO<SkillView> getAllSkills(SkillQuery query);

    /**
     * Get a skill by its ID.
     *
     * @param id the skill ID
     * @return the skill view
     */
    SkillView getSkillById(Integer id);

    /**
     * Get a skill by its abbreviation.
     *
     * @param abbreviation the skill abbreviation
     * @return the skill view
     */
    SkillView getSkillByAbbreviation(String abbreviation);
} 