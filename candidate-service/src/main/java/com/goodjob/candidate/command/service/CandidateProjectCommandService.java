package com.goodjob.candidate.command.service;

import com.goodjob.candidate.dto.CandidateProjectCommand;
import com.goodjob.candidate.dto.CandidateProjectResponse;

import java.util.List;

/**
 * Service interface for candidate project command operations.
 */
public interface CandidateProjectCommandService {

    /**
     * Adds a project to a candidate.
     *
     * @param userId the user ID
     * @param command the project command
     * @return the created project response
     */
    CandidateProjectResponse addProject(String userId, CandidateProjectCommand command);

    /**
     * Updates a project for a candidate.
     *
     * @param userId the user ID
     * @param projectId the project ID
     * @param command the project command
     * @return the updated project response
     */
    CandidateProjectResponse updateProject(String userId, String projectId, CandidateProjectCommand command);

    /**
     * Deletes a project from a candidate.
     *
     * @param userId the user ID
     * @param projectId the project ID
     */
    void deleteProject(String userId, String projectId);

    /**
     * Adds multiple projects to a candidate.
     *
     * @param userId the user ID
     * @param commands the list of project commands
     * @return the list of created project responses
     */
    List<CandidateProjectResponse> addProjects(String userId, List<CandidateProjectCommand> commands);

    /**
     * Deletes all projects from a candidate.
     *
     * @param userId the user ID
     */
    void deleteAllProjects(String userId);
} 