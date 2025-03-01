package com.goodjob.candidate.command.service.impl;

import com.goodjob.candidate.command.service.CandidateEducationCommandService;
import com.goodjob.candidate.dto.CandidateEducationCommand;
import com.goodjob.candidate.dto.CandidateEducationResponse;
import com.goodjob.candidate.entity.Candidate;
import com.goodjob.candidate.entity.CandidateEducation;
import com.goodjob.candidate.exception.CandidateNotFoundException;
import com.goodjob.candidate.exception.ResourceNotFoundException;
import com.goodjob.candidate.repository.CandidateEducationRepository;
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
 * Implementation of the CandidateEducationCommandService interface.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CandidateEducationCommandServiceImpl implements CandidateEducationCommandService {

    private final CandidateRepository candidateRepository;
    private final CandidateEducationRepository educationRepository;

    /**
     * Adds an education record to a candidate.
     *
     * @param userId the user ID
     * @param command the education command
     * @return the created education response
     */
    @Override
    public CandidateEducationResponse addEducation(String userId, CandidateEducationCommand command) {
        log.info("Adding education for user ID: {}", userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        validateEducationDates(command);
        
        CandidateEducation education = CandidateEducation.builder()
                .institution(command.getInstitution())
                .degree(command.getDegree())
                .fieldOfStudy(command.getFieldOfStudy())
                .location(command.getLocation())
                .startDate(command.getStartDate())
                .endDate(command.isCurrentlyEnrolled() ? null : command.getEndDate())
                .currentlyEnrolled(command.isCurrentlyEnrolled())
                .grade(command.getGrade())
                .gradeScale(command.getGradeScale())
                .activitiesAndSocieties(command.getActivitiesAndSocieties())
                .description(command.getDescription())
                .build();
        
        candidate.addEducation(education);
        candidateRepository.save(candidate);
        
        log.info("Education added: {} at {}", education.getDegree(), education.getInstitution());
        
        return CandidateEducationResponse.fromEntity(education);
    }

    /**
     * Updates an education record for a candidate.
     *
     * @param userId the user ID
     * @param educationId the education ID
     * @param command the education command
     * @return the updated education response
     */
    @Override
    public CandidateEducationResponse updateEducation(String userId, String educationId, CandidateEducationCommand command) {
        log.info("Updating education ID: {} for user ID: {}", educationId, userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        CandidateEducation education = educationRepository.findByCandidateIdAndId(candidate.getId().toString(), educationId)
                .orElseThrow(() -> new ResourceNotFoundException("Education not found with ID: " + educationId));
        
        validateEducationDates(command);
        
        education.setInstitution(command.getInstitution());
        education.setDegree(command.getDegree());
        education.setFieldOfStudy(command.getFieldOfStudy());
        education.setLocation(command.getLocation());
        education.setStartDate(command.getStartDate());
        education.setEndDate(command.isCurrentlyEnrolled() ? null : command.getEndDate());
        education.setCurrentlyEnrolled(command.isCurrentlyEnrolled());
        education.setGrade(command.getGrade());
        education.setGradeScale(command.getGradeScale());
        education.setActivitiesAndSocieties(command.getActivitiesAndSocieties());
        education.setDescription(command.getDescription());
        
        educationRepository.save(education);
        
        log.info("Education updated: {} at {}", education.getDegree(), education.getInstitution());
        
        return CandidateEducationResponse.fromEntity(education);
    }

    /**
     * Deletes an education record from a candidate.
     *
     * @param userId the user ID
     * @param educationId the education ID
     */
    @Override
    public void deleteEducation(String userId, String educationId) {
        log.info("Deleting education ID: {} for user ID: {}", educationId, userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        CandidateEducation education = educationRepository.findByCandidateIdAndId(candidate.getId().toString(), educationId)
                .orElseThrow(() -> new ResourceNotFoundException("Education not found with ID: " + educationId));
        
        candidate.removeEducation(education);
        candidateRepository.save(candidate);
        
        log.info("Education deleted: {} at {}", education.getDegree(), education.getInstitution());
    }

    /**
     * Adds multiple education records to a candidate.
     *
     * @param userId the user ID
     * @param commands the list of education commands
     * @return the list of created education responses
     */
    @Override
    public List<CandidateEducationResponse> addEducations(String userId, List<CandidateEducationCommand> commands) {
        log.info("Adding multiple education records for user ID: {}", userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        List<CandidateEducation> educations = new ArrayList<>();
        
        for (CandidateEducationCommand command : commands) {
            validateEducationDates(command);
            
            CandidateEducation education = CandidateEducation.builder()
                    .institution(command.getInstitution())
                    .degree(command.getDegree())
                    .fieldOfStudy(command.getFieldOfStudy())
                    .location(command.getLocation())
                    .startDate(command.getStartDate())
                    .endDate(command.isCurrentlyEnrolled() ? null : command.getEndDate())
                    .currentlyEnrolled(command.isCurrentlyEnrolled())
                    .grade(command.getGrade())
                    .gradeScale(command.getGradeScale())
                    .activitiesAndSocieties(command.getActivitiesAndSocieties())
                    .description(command.getDescription())
                    .build();
            
            candidate.addEducation(education);
            educations.add(education);
        }
        
        candidateRepository.save(candidate);
        
        log.info("{} education records added", educations.size());
        
        return educations.stream()
                .map(CandidateEducationResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Deletes all education records from a candidate.
     *
     * @param userId the user ID
     */
    @Override
    public void deleteAllEducations(String userId) {
        log.info("Deleting all education records for user ID: {}", userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        educationRepository.deleteByCandidateId(candidate.getId().toString());
        
        log.info("All education records deleted for candidate ID: {}", candidate.getId());
    }
    
    /**
     * Validates the education dates.
     *
     * @param command the education command
     */
    private void validateEducationDates(CandidateEducationCommand command) {
        if (!command.isCurrentlyEnrolled() && command.getEndDate() == null) {
            throw new IllegalArgumentException("End date is required when not currently enrolled");
        }
        
        if (!command.isCurrentlyEnrolled() && command.getEndDate() != null && 
            command.getStartDate().isAfter(command.getEndDate())) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        
        if (!command.isCurrentlyEnrolled() && command.getEndDate() != null && 
            command.getEndDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("End date cannot be in the future");
        }
    }
} 