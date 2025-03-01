package com.goodjob.candidate.command.service.impl;

import com.goodjob.candidate.command.service.CandidateExperienceCommandService;
import com.goodjob.candidate.dto.CandidateExperienceCommand;
import com.goodjob.candidate.dto.CandidateExperienceResponse;
import com.goodjob.candidate.entity.Candidate;
import com.goodjob.candidate.entity.CandidateExperience;
import com.goodjob.candidate.exception.CandidateNotFoundException;
import com.goodjob.candidate.exception.ResourceNotFoundException;
import com.goodjob.candidate.repository.CandidateExperienceRepository;
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
 * Implementation of the CandidateExperienceCommandService interface.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CandidateExperienceCommandServiceImpl implements CandidateExperienceCommandService {

    private final CandidateRepository candidateRepository;
    private final CandidateExperienceRepository experienceRepository;

    /**
     * Adds an experience to a candidate.
     *
     * @param userId the user ID
     * @param command the experience command
     * @return the created experience response
     */
    @Override
    public CandidateExperienceResponse addExperience(String userId, CandidateExperienceCommand command) {
        log.info("Adding experience for user ID: {}", userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        validateExperienceDates(command);
        
        CandidateExperience experience = CandidateExperience.builder()
                .jobTitle(command.getJobTitle())
                .company(command.getCompany())
                .location(command.getLocation())
                .startDate(command.getStartDate())
                .endDate(command.isCurrentlyWorking() ? null : command.getEndDate())
                .currentlyWorking(command.isCurrentlyWorking())
                .description(command.getDescription())
                .build();
        
        candidate.addExperience(experience);
        candidateRepository.save(candidate);
        
        log.info("Experience added: {} at {}", experience.getJobTitle(), experience.getCompany());
        
        return CandidateExperienceResponse.fromEntity(experience);
    }

    /**
     * Updates an experience for a candidate.
     *
     * @param userId the user ID
     * @param experienceId the experience ID
     * @param command the experience command
     * @return the updated experience response
     */
    @Override
    public CandidateExperienceResponse updateExperience(String userId, String experienceId, CandidateExperienceCommand command) {
        log.info("Updating experience ID: {} for user ID: {}", experienceId, userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        CandidateExperience experience = experienceRepository.findByCandidateIdAndId(candidate.getId().toString(), experienceId)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found with ID: " + experienceId));
        
        validateExperienceDates(command);
        
        experience.setJobTitle(command.getJobTitle());
        experience.setCompany(command.getCompany());
        experience.setLocation(command.getLocation());
        experience.setStartDate(command.getStartDate());
        experience.setEndDate(command.isCurrentlyWorking() ? null : command.getEndDate());
        experience.setCurrentlyWorking(command.isCurrentlyWorking());
        experience.setDescription(command.getDescription());
        
        experienceRepository.save(experience);
        
        log.info("Experience updated: {} at {}", experience.getJobTitle(), experience.getCompany());
        
        return CandidateExperienceResponse.fromEntity(experience);
    }

    /**
     * Deletes an experience from a candidate.
     *
     * @param userId the user ID
     * @param experienceId the experience ID
     */
    @Override
    public void deleteExperience(String userId, String experienceId) {
        log.info("Deleting experience ID: {} for user ID: {}", experienceId, userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        CandidateExperience experience = experienceRepository.findByCandidateIdAndId(candidate.getId().toString(), experienceId)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found with ID: " + experienceId));
        
        candidate.removeExperience(experience);
        candidateRepository.save(candidate);
        
        log.info("Experience deleted: {} at {}", experience.getJobTitle(), experience.getCompany());
    }

    /**
     * Adds multiple experiences to a candidate.
     *
     * @param userId the user ID
     * @param commands the list of experience commands
     * @return the list of created experience responses
     */
    @Override
    public List<CandidateExperienceResponse> addExperiences(String userId, List<CandidateExperienceCommand> commands) {
        log.info("Adding multiple experiences for user ID: {}", userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        List<CandidateExperience> experiences = new ArrayList<>();
        
        for (CandidateExperienceCommand command : commands) {
            validateExperienceDates(command);
            
            CandidateExperience experience = CandidateExperience.builder()
                    .jobTitle(command.getJobTitle())
                    .company(command.getCompany())
                    .location(command.getLocation())
                    .startDate(command.getStartDate())
                    .endDate(command.isCurrentlyWorking() ? null : command.getEndDate())
                    .currentlyWorking(command.isCurrentlyWorking())
                    .description(command.getDescription())
                    .build();
            
            candidate.addExperience(experience);
            experiences.add(experience);
        }
        
        candidateRepository.save(candidate);
        
        log.info("{} experiences added", experiences.size());
        
        return experiences.stream()
                .map(CandidateExperienceResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Deletes all experiences from a candidate.
     *
     * @param userId the user ID
     */
    @Override
    public void deleteAllExperiences(String userId) {
        log.info("Deleting all experiences for user ID: {}", userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        experienceRepository.deleteByCandidateId(candidate.getId().toString());
        
        log.info("All experiences deleted for candidate ID: {}", candidate.getId());
    }
    
    /**
     * Validates the experience dates.
     *
     * @param command the experience command
     */
    private void validateExperienceDates(CandidateExperienceCommand command) {
        if (!command.isCurrentlyWorking() && command.getEndDate() == null) {
            throw new IllegalArgumentException("End date is required when not currently working");
        }
        
        if (!command.isCurrentlyWorking() && command.getEndDate() != null && 
            command.getStartDate().isAfter(command.getEndDate())) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        
        if (!command.isCurrentlyWorking() && command.getEndDate() != null && 
            command.getEndDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("End date cannot be in the future");
        }
    }
} 