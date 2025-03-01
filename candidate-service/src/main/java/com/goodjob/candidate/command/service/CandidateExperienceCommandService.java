package com.goodjob.candidate.command.service;

import com.goodjob.candidate.dto.CandidateExperienceCommand;
import com.goodjob.candidate.dto.CandidateExperienceResponse;

import java.util.List;

/**
 * Service interface for candidate experience command operations.
 */
public interface CandidateExperienceCommandService {

    /**
     * Adds an experience to a candidate.
     *
     * @param userId the user ID
     * @param command the experience command
     * @return the created experience response
     */
    CandidateExperienceResponse addExperience(String userId, CandidateExperienceCommand command);

    /**
     * Updates an experience for a candidate.
     *
     * @param userId the user ID
     * @param experienceId the experience ID
     * @param command the experience command
     * @return the updated experience response
     */
    CandidateExperienceResponse updateExperience(String userId, String experienceId, CandidateExperienceCommand command);

    /**
     * Deletes an experience from a candidate.
     *
     * @param userId the user ID
     * @param experienceId the experience ID
     */
    void deleteExperience(String userId, String experienceId);

    /**
     * Adds multiple experiences to a candidate.
     *
     * @param userId the user ID
     * @param commands the list of experience commands
     * @return the list of created experience responses
     */
    List<CandidateExperienceResponse> addExperiences(String userId, List<CandidateExperienceCommand> commands);

    /**
     * Deletes all experiences from a candidate.
     *
     * @param userId the user ID
     */
    void deleteAllExperiences(String userId);
} 