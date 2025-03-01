package com.goodjob.candidate.command.service;

import com.goodjob.candidate.dto.CandidateCommand;
import com.goodjob.candidate.dto.CandidateResponse;

/**
 * Service interface for candidate command operations.
 */
public interface CandidateCommandService {

    /**
     * Creates a new candidate.
     *
     * @param userId the user ID
     * @param command the candidate command
     * @return the created candidate response
     */
    CandidateResponse createCandidate(String userId, CandidateCommand command);

    /**
     * Updates an existing candidate.
     *
     * @param userId the user ID
     * @param command the candidate command
     * @return the updated candidate response
     */
    CandidateResponse updateCandidate(String userId, CandidateCommand command);

    /**
     * Deletes a candidate.
     *
     * @param userId the user ID
     */
    void deleteCandidate(String userId);

    /**
     * Updates the candidate's profile visibility.
     *
     * @param userId the user ID
     * @param visible the visibility status
     * @return the updated candidate response
     */
    CandidateResponse updateProfileVisibility(String userId, boolean visible);

    /**
     * Updates the candidate's open to work status.
     *
     * @param userId the user ID
     * @param openToWork the open to work status
     * @return the updated candidate response
     */
    CandidateResponse updateOpenToWorkStatus(String userId, boolean openToWork);

    /**
     * Updates the candidate's open to relocate status.
     *
     * @param userId the user ID
     * @param openToRelocate the open to relocate status
     * @return the updated candidate response
     */
    CandidateResponse updateOpenToRelocateStatus(String userId, boolean openToRelocate);
} 