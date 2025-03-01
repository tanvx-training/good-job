package com.goodjob.candidate.command.service.impl;

import com.goodjob.candidate.command.service.CandidateProjectCommandService;
import com.goodjob.candidate.dto.CandidateProjectCommand;
import com.goodjob.candidate.dto.CandidateProjectResponse;
import com.goodjob.candidate.entity.Candidate;
import com.goodjob.candidate.entity.CandidateProject;
import com.goodjob.candidate.exception.CandidateNotFoundException;
import com.goodjob.candidate.exception.ResourceNotFoundException;
import com.goodjob.candidate.repository.CandidateProjectRepository;
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
 * Implementation of the CandidateProjectCommandService interface.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CandidateProjectCommandServiceImpl implements CandidateProjectCommandService {

    private final CandidateRepository candidateRepository;
    private final CandidateProjectRepository projectRepository;

    /**
     * Adds a project to a candidate.
     *
     * @param userId the user ID
     * @param command the project command
     * @return the created project response
     */
    @Override
    public CandidateProjectResponse addProject(String userId, CandidateProjectCommand command) {
        log.info("Adding project for user ID: {}", userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        validateProjectDates(command);
        
        CandidateProject project = CandidateProject.builder()
                .name(command.getName())
                .description(command.getDescription())
                .projectUrl(command.getProjectUrl())
                .repositoryUrl(command.getRepositoryUrl())
                .startDate(command.getStartDate())
                .endDate(command.isCurrentlyWorking() ? null : command.getEndDate())
                .currentlyWorking(command.isCurrentlyWorking())
                .technologies(command.getTechnologies())
                .build();
        
        candidate.addProject(project);
        candidateRepository.save(candidate);
        
        log.info("Project added: {}", project.getName());
        
        return CandidateProjectResponse.fromEntity(project);
    }

    /**
     * Updates a project for a candidate.
     *
     * @param userId the user ID
     * @param projectId the project ID
     * @param command the project command
     * @return the updated project response
     */
    @Override
    public CandidateProjectResponse updateProject(String userId, String projectId, CandidateProjectCommand command) {
        log.info("Updating project ID: {} for user ID: {}", projectId, userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        CandidateProject project = projectRepository.findByCandidateIdAndId(candidate.getId().toString(), projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));
        
        validateProjectDates(command);
        
        project.setName(command.getName());
        project.setDescription(command.getDescription());
        project.setProjectUrl(command.getProjectUrl());
        project.setRepositoryUrl(command.getRepositoryUrl());
        project.setStartDate(command.getStartDate());
        project.setEndDate(command.isCurrentlyWorking() ? null : command.getEndDate());
        project.setCurrentlyWorking(command.isCurrentlyWorking());
        project.setTechnologies(command.getTechnologies());
        
        projectRepository.save(project);
        
        log.info("Project updated: {}", project.getName());
        
        return CandidateProjectResponse.fromEntity(project);
    }

    /**
     * Deletes a project from a candidate.
     *
     * @param userId the user ID
     * @param projectId the project ID
     */
    @Override
    public void deleteProject(String userId, String projectId) {
        log.info("Deleting project ID: {} for user ID: {}", projectId, userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        CandidateProject project = projectRepository.findByCandidateIdAndId(candidate.getId().toString(), projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));
        
        candidate.removeProject(project);
        candidateRepository.save(candidate);
        
        log.info("Project deleted: {}", project.getName());
    }

    /**
     * Adds multiple projects to a candidate.
     *
     * @param userId the user ID
     * @param commands the list of project commands
     * @return the list of created project responses
     */
    @Override
    public List<CandidateProjectResponse> addProjects(String userId, List<CandidateProjectCommand> commands) {
        log.info("Adding multiple projects for user ID: {}", userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        List<CandidateProject> projects = new ArrayList<>();
        
        for (CandidateProjectCommand command : commands) {
            validateProjectDates(command);
            
            CandidateProject project = CandidateProject.builder()
                    .name(command.getName())
                    .description(command.getDescription())
                    .projectUrl(command.getProjectUrl())
                    .repositoryUrl(command.getRepositoryUrl())
                    .startDate(command.getStartDate())
                    .endDate(command.isCurrentlyWorking() ? null : command.getEndDate())
                    .currentlyWorking(command.isCurrentlyWorking())
                    .technologies(command.getTechnologies())
                    .build();
            
            candidate.addProject(project);
            projects.add(project);
        }
        
        candidateRepository.save(candidate);
        
        log.info("{} projects added", projects.size());
        
        return projects.stream()
                .map(CandidateProjectResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Deletes all projects from a candidate.
     *
     * @param userId the user ID
     */
    @Override
    public void deleteAllProjects(String userId) {
        log.info("Deleting all projects for user ID: {}", userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        projectRepository.deleteByCandidateId(candidate.getId().toString());
        
        log.info("All projects deleted for candidate ID: {}", candidate.getId());
    }
    
    /**
     * Validates the project dates.
     *
     * @param command the project command
     */
    private void validateProjectDates(CandidateProjectCommand command) {
        if (!command.isCurrentlyWorking() && command.getEndDate() == null) {
            throw new IllegalArgumentException("End date is required when not currently working on the project");
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