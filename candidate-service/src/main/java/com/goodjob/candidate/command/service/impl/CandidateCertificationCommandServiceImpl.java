package com.goodjob.candidate.command.service.impl;

import com.goodjob.candidate.command.service.CandidateCertificationCommandService;
import com.goodjob.candidate.dto.CandidateCertificationCommand;
import com.goodjob.candidate.dto.CandidateCertificationResponse;
import com.goodjob.candidate.entity.Candidate;
import com.goodjob.candidate.entity.CandidateCertification;
import com.goodjob.candidate.exception.CandidateNotFoundException;
import com.goodjob.candidate.exception.ResourceNotFoundException;
import com.goodjob.candidate.repository.CandidateCertificationRepository;
import com.goodjob.candidate.repository.CandidateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the CandidateCertificationCommandService interface.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CandidateCertificationCommandServiceImpl implements CandidateCertificationCommandService {

    private final CandidateRepository candidateRepository;
    private final CandidateCertificationRepository certificationRepository;

    /**
     * Adds a certification to a candidate.
     *
     * @param userId the user ID
     * @param command the certification command
     * @return the created certification response
     */
    @Override
    public CandidateCertificationResponse addCertification(String userId, CandidateCertificationCommand command) {
        log.info("Adding certification for user ID: {}", userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        validateCertificationDates(command);
        
        CandidateCertification certification = CandidateCertification.builder()
                .name(command.getName())
                .issuingOrganization(command.getIssuingOrganization())
                .credentialId(command.getCredentialId())
                .credentialUrl(command.getCredentialUrl())
                .issueDate(command.getIssueDate())
                .expirationDate(command.isDoesNotExpire() ? null : command.getExpirationDate())
                .doesNotExpire(command.isDoesNotExpire())
                .build();
        
        candidate.addCertification(certification);
        candidateRepository.save(candidate);
        
        log.info("Certification added: {} from {}", certification.getName(), certification.getIssuingOrganization());
        
        return CandidateCertificationResponse.fromEntity(certification);
    }

    /**
     * Updates a certification for a candidate.
     *
     * @param userId the user ID
     * @param certificationId the certification ID
     * @param command the certification command
     * @return the updated certification response
     */
    @Override
    public CandidateCertificationResponse updateCertification(String userId, String certificationId, CandidateCertificationCommand command) {
        log.info("Updating certification ID: {} for user ID: {}", certificationId, userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        CandidateCertification certification = certificationRepository.findByCandidateIdAndId(candidate.getId().toString(), certificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Certification not found with ID: " + certificationId));
        
        validateCertificationDates(command);
        
        certification.setName(command.getName());
        certification.setIssuingOrganization(command.getIssuingOrganization());
        certification.setCredentialId(command.getCredentialId());
        certification.setCredentialUrl(command.getCredentialUrl());
        certification.setIssueDate(command.getIssueDate());
        certification.setExpirationDate(command.isDoesNotExpire() ? null : command.getExpirationDate());
        certification.setDoesNotExpire(command.isDoesNotExpire());
        
        certificationRepository.save(certification);
        
        log.info("Certification updated: {} from {}", certification.getName(), certification.getIssuingOrganization());
        
        return CandidateCertificationResponse.fromEntity(certification);
    }

    /**
     * Deletes a certification from a candidate.
     *
     * @param userId the user ID
     * @param certificationId the certification ID
     */
    @Override
    public void deleteCertification(String userId, String certificationId) {
        log.info("Deleting certification ID: {} for user ID: {}", certificationId, userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        CandidateCertification certification = certificationRepository.findByCandidateIdAndId(candidate.getId().toString(), certificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Certification not found with ID: " + certificationId));
        
        candidate.removeCertification(certification);
        candidateRepository.save(candidate);
        
        log.info("Certification deleted: {} from {}", certification.getName(), certification.getIssuingOrganization());
    }

    /**
     * Adds multiple certifications to a candidate.
     *
     * @param userId the user ID
     * @param commands the list of certification commands
     * @return the list of created certification responses
     */
    @Override
    public List<CandidateCertificationResponse> addCertifications(String userId, List<CandidateCertificationCommand> commands) {
        log.info("Adding multiple certifications for user ID: {}", userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        List<CandidateCertification> certifications = new ArrayList<>();
        
        for (CandidateCertificationCommand command : commands) {
            validateCertificationDates(command);
            
            CandidateCertification certification = CandidateCertification.builder()
                    .name(command.getName())
                    .issuingOrganization(command.getIssuingOrganization())
                    .credentialId(command.getCredentialId())
                    .credentialUrl(command.getCredentialUrl())
                    .issueDate(command.getIssueDate())
                    .expirationDate(command.isDoesNotExpire() ? null : command.getExpirationDate())
                    .doesNotExpire(command.isDoesNotExpire())
                    .build();
            
            candidate.addCertification(certification);
            certifications.add(certification);
        }
        
        candidateRepository.save(candidate);
        
        log.info("{} certifications added", certifications.size());
        
        return certifications.stream()
                .map(CandidateCertificationResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Deletes all certifications from a candidate.
     *
     * @param userId the user ID
     */
    @Override
    public void deleteAllCertifications(String userId) {
        log.info("Deleting all certifications for user ID: {}", userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        certificationRepository.deleteByCandidateId(candidate.getId().toString());
        
        log.info("All certifications deleted for candidate ID: {}", candidate.getId());
    }
    
    /**
     * Validates the certification dates.
     *
     * @param command the certification command
     */
    private void validateCertificationDates(CandidateCertificationCommand command) {
        if (!command.isDoesNotExpire() && command.getExpirationDate() == null) {
            throw new IllegalArgumentException("Expiration date is required when certification expires");
        }
        
        if (!command.isDoesNotExpire() && command.getExpirationDate() != null && 
            command.getIssueDate().isAfter(command.getExpirationDate())) {
            throw new IllegalArgumentException("Issue date cannot be after expiration date");
        }
        
        if (command.getIssueDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Issue date cannot be in the future");
        }
    }
} 