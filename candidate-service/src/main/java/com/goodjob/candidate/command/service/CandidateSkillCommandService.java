package com.goodjob.candidate.command.service;

import com.goodjob.candidate.dto.CandidateSkillCommand;
import com.goodjob.candidate.dto.CandidateSkillResponse;

import java.util.List;

/**
 * Service interface for candidate skill command operations.
 */
public interface CandidateSkillCommandService {

    /**
     * Adds a skill to a candidate.
     *
     * @param userId the user ID
     * @param command the skill command
     * @return the created skill response
     */
    CandidateSkillResponse addSkill(String userId, CandidateSkillCommand command);

    /**
     * Updates a skill for a candidate.
     *
     * @param userId the user ID
     * @param skillId the skill ID
     * @param command the skill command
     * @return the updated skill response
     */
    CandidateSkillResponse updateSkill(String userId, String skillId, CandidateSkillCommand command);

    /**
     * Deletes a skill from a candidate.
     *
     * @param userId the user ID
     * @param skillId the skill ID
     */
    void deleteSkill(String userId, String skillId);

    /**
     * Adds multiple skills to a candidate.
     *
     * @param userId the user ID
     * @param commands the list of skill commands
     * @return the list of created skill responses
     */
    List<CandidateSkillResponse> addSkills(String userId, List<CandidateSkillCommand> commands);

    /**
     * Deletes all skills from a candidate.
     *
     * @param userId the user ID
     */
    void deleteAllSkills(String userId);
} 