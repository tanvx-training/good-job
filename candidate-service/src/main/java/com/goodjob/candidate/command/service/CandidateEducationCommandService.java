package com.goodjob.candidate.command.service;

import com.goodjob.candidate.dto.CandidateEducationCommand;
import com.goodjob.candidate.dto.CandidateEducationResponse;

import java.util.List;

/**
 * Service interface for candidate education command operations.
 */
public interface CandidateEducationCommandService {

    /**
     * Adds an education record to a candidate.
     *
     * @param userId the user ID
     * @param command the education command
     * @return the created education response
     */
    CandidateEducationResponse addEducation(String userId, CandidateEducationCommand command);

    /**
     * Updates an education record for a candidate.
     *
     * @param userId the user ID
     * @param educationId the education ID
     * @param command the education command
     * @return the updated education response
     */
    CandidateEducationResponse updateEducation(String userId, String educationId, CandidateEducationCommand command);

    /**
     * Deletes an education record from a candidate.
     *
     * @param userId the user ID
     * @param educationId the education ID
     */
    void deleteEducation(String userId, String educationId);

    /**
     * Adds multiple education records to a candidate.
     *
     * @param userId the user ID
     * @param commands the list of education commands
     * @return the list of created education responses
     */
    List<CandidateEducationResponse> addEducations(String userId, List<CandidateEducationCommand> commands);

    /**
     * Deletes all education records from a candidate.
     *
     * @param userId the user ID
     */
    void deleteAllEducations(String userId);
} 