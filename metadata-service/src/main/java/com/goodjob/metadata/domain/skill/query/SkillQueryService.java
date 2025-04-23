package com.goodjob.metadata.domain.skill.query;

import com.goodjob.common.application.dto.response.PageResponseDTO;
import com.goodjob.metadata.domain.skill.dto.SkillQuery;
import com.goodjob.metadata.domain.skill.dto.SkillView;

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

    List<SkillView> getAllByIdList(List<Integer> idList);
}