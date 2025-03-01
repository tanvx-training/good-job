package com.goodjob.candidate.command.service.impl;

import com.goodjob.candidate.command.service.CandidateCommandService;
import com.goodjob.candidate.dto.CandidateCommand;
import com.goodjob.candidate.dto.CandidateResponse;
import com.goodjob.candidate.entity.Candidate;
import com.goodjob.candidate.exception.CandidateNotFoundException;
import com.goodjob.candidate.exception.UnauthorizedAccessException;
import com.goodjob.candidate.repository.CandidateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the CandidateCommandService interface.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CandidateCommandServiceImpl implements CandidateCommandService {

    private final CandidateRepository candidateRepository;

    /**
     * Creates a new candidate.
     *
     * @param userId the user ID
     * @param command the candidate command
     * @return the created candidate response
     */
    @Override
    public CandidateResponse createCandidate(String userId, CandidateCommand command) {
        log.info("Creating candidate for user ID: {}", userId);
        
        if (candidateRepository.existsByUserId(userId)) {
            log.warn("Candidate already exists for user ID: {}", userId);
            throw new IllegalStateException("Candidate already exists for this user");
        }
        
        Candidate candidate = Candidate.builder()
                .userId(userId)
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .headline(command.getHeadline())
                .summary(command.getSummary())
                .email(command.getEmail())
                .phone(command.getPhone())
                .location(command.getLocation())
                .profilePictureUrl(command.getProfilePictureUrl())
                .resumeUrl(command.getResumeUrl())
                .dateOfBirth(command.getDateOfBirth())
                .currentJobTitle(command.getCurrentJobTitle())
                .currentCompany(command.getCurrentCompany())
                .highestEducation(command.getHighestEducation())
                .educationInstitution(command.getEducationInstitution())
                .linkedinUrl(command.getLinkedinUrl())
                .githubUrl(command.getGithubUrl())
                .portfolioUrl(command.getPortfolioUrl())
                .openToWork(command.isOpenToWork())
                .openToRelocate(command.isOpenToRelocate())
                .profileVisible(command.isProfileVisible())
                .profileCompleted(false)
                .build();
        
        Candidate savedCandidate = candidateRepository.save(candidate);
        log.info("Candidate created with ID: {}", savedCandidate.getId());
        
        return CandidateResponse.fromEntity(savedCandidate);
    }

    /**
     * Updates an existing candidate.
     *
     * @param userId the user ID
     * @param command the candidate command
     * @return the updated candidate response
     */
    @Override
    public CandidateResponse updateCandidate(String userId, CandidateCommand command) {
        log.info("Updating candidate for user ID: {}", userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        candidate.setFirstName(command.getFirstName());
        candidate.setLastName(command.getLastName());
        candidate.setHeadline(command.getHeadline());
        candidate.setSummary(command.getSummary());
        candidate.setEmail(command.getEmail());
        candidate.setPhone(command.getPhone());
        candidate.setLocation(command.getLocation());
        candidate.setProfilePictureUrl(command.getProfilePictureUrl());
        candidate.setResumeUrl(command.getResumeUrl());
        candidate.setDateOfBirth(command.getDateOfBirth());
        candidate.setCurrentJobTitle(command.getCurrentJobTitle());
        candidate.setCurrentCompany(command.getCurrentCompany());
        candidate.setHighestEducation(command.getHighestEducation());
        candidate.setEducationInstitution(command.getEducationInstitution());
        candidate.setLinkedinUrl(command.getLinkedinUrl());
        candidate.setGithubUrl(command.getGithubUrl());
        candidate.setPortfolioUrl(command.getPortfolioUrl());
        candidate.setOpenToWork(command.isOpenToWork());
        candidate.setOpenToRelocate(command.isOpenToRelocate());
        candidate.setProfileVisible(command.isProfileVisible());
        
        // Check if profile is completed
        boolean isProfileCompleted = isProfileCompleted(candidate);
        candidate.setProfileCompleted(isProfileCompleted);
        
        Candidate updatedCandidate = candidateRepository.save(candidate);
        log.info("Candidate updated with ID: {}", updatedCandidate.getId());
        
        return CandidateResponse.fromEntity(updatedCandidate);
    }

    /**
     * Deletes a candidate.
     *
     * @param userId the user ID
     */
    @Override
    public void deleteCandidate(String userId) {
        log.info("Deleting candidate for user ID: {}", userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        candidateRepository.delete(candidate);
        log.info("Candidate deleted with ID: {}", candidate.getId());
    }

    /**
     * Updates the candidate's profile visibility.
     *
     * @param userId the user ID
     * @param visible the visibility status
     * @return the updated candidate response
     */
    @Override
    public CandidateResponse updateProfileVisibility(String userId, boolean visible) {
        log.info("Updating profile visibility for user ID: {}", userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        candidate.setProfileVisible(visible);
        
        Candidate updatedCandidate = candidateRepository.save(candidate);
        log.info("Profile visibility updated for candidate ID: {}", updatedCandidate.getId());
        
        return CandidateResponse.fromEntity(updatedCandidate);
    }

    /**
     * Updates the candidate's open to work status.
     *
     * @param userId the user ID
     * @param openToWork the open to work status
     * @return the updated candidate response
     */
    @Override
    public CandidateResponse updateOpenToWorkStatus(String userId, boolean openToWork) {
        log.info("Updating open to work status for user ID: {}", userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        candidate.setOpenToWork(openToWork);
        
        Candidate updatedCandidate = candidateRepository.save(candidate);
        log.info("Open to work status updated for candidate ID: {}", updatedCandidate.getId());
        
        return CandidateResponse.fromEntity(updatedCandidate);
    }

    /**
     * Updates the candidate's open to relocate status.
     *
     * @param userId the user ID
     * @param openToRelocate the open to relocate status
     * @return the updated candidate response
     */
    @Override
    public CandidateResponse updateOpenToRelocateStatus(String userId, boolean openToRelocate) {
        log.info("Updating open to relocate status for user ID: {}", userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        candidate.setOpenToRelocate(openToRelocate);
        
        Candidate updatedCandidate = candidateRepository.save(candidate);
        log.info("Open to relocate status updated for candidate ID: {}", updatedCandidate.getId());
        
        return CandidateResponse.fromEntity(updatedCandidate);
    }
    
    /**
     * Checks if a candidate's profile is completed.
     * A profile is considered completed if it has all required fields filled.
     *
     * @param candidate the candidate
     * @return true if the profile is completed, false otherwise
     */
    private boolean isProfileCompleted(Candidate candidate) {
        return candidate.getFirstName() != null && !candidate.getFirstName().isEmpty() &&
               candidate.getLastName() != null && !candidate.getLastName().isEmpty() &&
               candidate.getEmail() != null && !candidate.getEmail().isEmpty() &&
               candidate.getHeadline() != null && !candidate.getHeadline().isEmpty() &&
               candidate.getSummary() != null && !candidate.getSummary().isEmpty() &&
               candidate.getLocation() != null && !candidate.getLocation().isEmpty() &&
               candidate.getCurrentJobTitle() != null && !candidate.getCurrentJobTitle().isEmpty() &&
               candidate.getHighestEducation() != null && !candidate.getHighestEducation().isEmpty() &&
               !candidate.getSkills().isEmpty();
    }
} 