package com.goodjob.candidate.controller;

import com.goodjob.candidate.command.service.CandidateProjectCommandService;
import com.goodjob.candidate.dto.CandidateProjectCommand;
import com.goodjob.candidate.dto.CandidateProjectResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for candidate project operations.
 */
@RestController
@RequestMapping("/api/v1/candidates")
@RequiredArgsConstructor
public class CandidateProjectController {

    private final CandidateProjectCommandService projectCommandService;

    /**
     * Adds a project to a candidate.
     *
     * @param userId the user ID
     * @param command the project command
     * @return the created project response
     */
    @PostMapping("/{userId}/projects")
    public ResponseEntity<CandidateProjectResponse> addProject(
            @PathVariable String userId,
            @Valid @RequestBody CandidateProjectCommand command) {
        CandidateProjectResponse response = projectCommandService.addProject(userId, command);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Updates a project for a candidate.
     *
     * @param userId the user ID
     * @param projectId the project ID
     * @param command the project command
     * @return the updated project response
     */
    @PutMapping("/{userId}/projects/{projectId}")
    public ResponseEntity<CandidateProjectResponse> updateProject(
            @PathVariable String userId,
            @PathVariable String projectId,
            @Valid @RequestBody CandidateProjectCommand command) {
        CandidateProjectResponse response = projectCommandService.updateProject(userId, projectId, command);
        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a project from a candidate.
     *
     * @param userId the user ID
     * @param projectId the project ID
     * @return the response entity
     */
    @DeleteMapping("/{userId}/projects/{projectId}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable String userId,
            @PathVariable String projectId) {
        projectCommandService.deleteProject(userId, projectId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Adds multiple projects to a candidate.
     *
     * @param userId the user ID
     * @param commands the list of project commands
     * @return the list of created project responses
     */
    @PostMapping("/{userId}/projects/batch")
    public ResponseEntity<List<CandidateProjectResponse>> addProjects(
            @PathVariable String userId,
            @Valid @RequestBody List<CandidateProjectCommand> commands) {
        List<CandidateProjectResponse> responses = projectCommandService.addProjects(userId, commands);
        return new ResponseEntity<>(responses, HttpStatus.CREATED);
    }

    /**
     * Deletes all projects from a candidate.
     *
     * @param userId the user ID
     * @return the response entity
     */
    @DeleteMapping("/{userId}/projects")
    public ResponseEntity<Void> deleteAllProjects(
            @PathVariable String userId) {
        projectCommandService.deleteAllProjects(userId);
        return ResponseEntity.noContent().build();
    }
} 