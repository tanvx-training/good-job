package com.goodjob.candidate.controller;

import com.goodjob.candidate.command.service.CandidateCertificationCommandService;
import com.goodjob.candidate.dto.CandidateCertificationCommand;
import com.goodjob.candidate.dto.CandidateCertificationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for candidate certification operations.
 */
@RestController
@RequestMapping("/api/v1/candidates")
@RequiredArgsConstructor
public class CandidateCertificationController {

    private final CandidateCertificationCommandService certificationCommandService;

    /**
     * Adds a certification to a candidate.
     *
     * @param userId the user ID
     * @param command the certification command
     * @return the created certification response
     */
    @PostMapping("/{userId}/certifications")
    public ResponseEntity<CandidateCertificationResponse> addCertification(
            @PathVariable String userId,
            @Valid @RequestBody CandidateCertificationCommand command) {
        CandidateCertificationResponse response = certificationCommandService.addCertification(userId, command);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Updates a certification for a candidate.
     *
     * @param userId the user ID
     * @param certificationId the certification ID
     * @param command the certification command
     * @return the updated certification response
     */
    @PutMapping("/{userId}/certifications/{certificationId}")
    public ResponseEntity<CandidateCertificationResponse> updateCertification(
            @PathVariable String userId,
            @PathVariable String certificationId,
            @Valid @RequestBody CandidateCertificationCommand command) {
        CandidateCertificationResponse response = certificationCommandService.updateCertification(userId, certificationId, command);
        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a certification from a candidate.
     *
     * @param userId the user ID
     * @param certificationId the certification ID
     * @return the response entity
     */
    @DeleteMapping("/{userId}/certifications/{certificationId}")
    public ResponseEntity<Void> deleteCertification(
            @PathVariable String userId,
            @PathVariable String certificationId) {
        certificationCommandService.deleteCertification(userId, certificationId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Adds multiple certifications to a candidate.
     *
     * @param userId the user ID
     * @param commands the list of certification commands
     * @return the list of created certification responses
     */
    @PostMapping("/{userId}/certifications/batch")
    public ResponseEntity<List<CandidateCertificationResponse>> addCertifications(
            @PathVariable String userId,
            @Valid @RequestBody List<CandidateCertificationCommand> commands) {
        List<CandidateCertificationResponse> responses = certificationCommandService.addCertifications(userId, commands);
        return new ResponseEntity<>(responses, HttpStatus.CREATED);
    }

    /**
     * Deletes all certifications from a candidate.
     *
     * @param userId the user ID
     * @return the response entity
     */
    @DeleteMapping("/{userId}/certifications")
    public ResponseEntity<Void> deleteAllCertifications(
            @PathVariable String userId) {
        certificationCommandService.deleteAllCertifications(userId);
        return ResponseEntity.noContent().build();
    }
} 