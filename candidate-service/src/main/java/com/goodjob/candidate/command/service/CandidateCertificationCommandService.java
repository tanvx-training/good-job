package com.goodjob.candidate.command.service;

import com.goodjob.candidate.dto.CandidateCertificationCommand;
import com.goodjob.candidate.dto.CandidateCertificationResponse;

import java.util.List;

/**
 * Service interface for candidate certification command operations.
 */
public interface CandidateCertificationCommandService {

    /**
     * Adds a certification to a candidate.
     *
     * @param userId the user ID
     * @param command the certification command
     * @return the created certification response
     */
    CandidateCertificationResponse addCertification(String userId, CandidateCertificationCommand command);

    /**
     * Updates a certification for a candidate.
     *
     * @param userId the user ID
     * @param certificationId the certification ID
     * @param command the certification command
     * @return the updated certification response
     */
    CandidateCertificationResponse updateCertification(String userId, String certificationId, CandidateCertificationCommand command);

    /**
     * Deletes a certification from a candidate.
     *
     * @param userId the user ID
     * @param certificationId the certification ID
     */
    void deleteCertification(String userId, String certificationId);

    /**
     * Adds multiple certifications to a candidate.
     *
     * @param userId the user ID
     * @param commands the list of certification commands
     * @return the list of created certification responses
     */
    List<CandidateCertificationResponse> addCertifications(String userId, List<CandidateCertificationCommand> commands);

    /**
     * Deletes all certifications from a candidate.
     *
     * @param userId the user ID
     */
    void deleteAllCertifications(String userId);
} 