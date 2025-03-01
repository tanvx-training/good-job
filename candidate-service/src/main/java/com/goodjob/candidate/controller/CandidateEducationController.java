package com.goodjob.candidate.controller;

import com.goodjob.candidate.command.service.CandidateEducationCommandService;
import com.goodjob.candidate.dto.CandidateEducationCommand;
import com.goodjob.candidate.dto.CandidateEducationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for candidate education operations.
 */
@RestController
@RequestMapping("/api/v1/candidates")
@RequiredArgsConstructor
public class CandidateEducationController {

    private final CandidateEducationCommandService educationCommandService;

    /**
     * Adds an education record to a candidate.
     *
     * @param userId the user ID
     * @param command the education command
     * @return the created education response
     */
    @PostMapping("/{userId}/educations")
    public ResponseEntity<CandidateEducationResponse> addEducation(
            @PathVariable String userId,
            @Valid @RequestBody CandidateEducationCommand command) {
        CandidateEducationResponse response = educationCommandService.addEducation(userId, command);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Updates an education record for a candidate.
     *
     * @param userId the user ID
     * @param educationId the education ID
     * @param command the education command
     * @return the updated education response
     */
    @PutMapping("/{userId}/educations/{educationId}")
    public ResponseEntity<CandidateEducationResponse> updateEducation(
            @PathVariable String userId,
            @PathVariable String educationId,
            @Valid @RequestBody CandidateEducationCommand command) {
        CandidateEducationResponse response = educationCommandService.updateEducation(userId, educationId, command);
        return ResponseEntity.ok(response);
    }

    /**
     * Deletes an education record from a candidate.
     *
     * @param userId the user ID
     * @param educationId the education ID
     * @return the response entity
     */
    @DeleteMapping("/{userId}/educations/{educationId}")
    public ResponseEntity<Void> deleteEducation(
            @PathVariable String userId,
            @PathVariable String educationId) {
        educationCommandService.deleteEducation(userId, educationId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Adds multiple education records to a candidate.
     *
     * @param userId the user ID
     * @param commands the list of education commands
     * @return the list of created education responses
     */
    @PostMapping("/{userId}/educations/batch")
    public ResponseEntity<List<CandidateEducationResponse>> addEducations(
            @PathVariable String userId,
            @Valid @RequestBody List<CandidateEducationCommand> commands) {
        List<CandidateEducationResponse> responses = educationCommandService.addEducations(userId, commands);
        return new ResponseEntity<>(responses, HttpStatus.CREATED);
    }

    /**
     * Deletes all education records from a candidate.
     *
     * @param userId the user ID
     * @return the response entity
     */
    @DeleteMapping("/{userId}/educations")
    public ResponseEntity<Void> deleteAllEducations(
            @PathVariable String userId) {
        educationCommandService.deleteAllEducations(userId);
        return ResponseEntity.noContent().build();
    }
} 