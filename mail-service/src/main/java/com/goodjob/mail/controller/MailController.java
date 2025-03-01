package com.goodjob.mail.controller;

import com.goodjob.mail.dto.EmailRequest;
import com.goodjob.mail.dto.EmailStatusResponse;
import com.goodjob.mail.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for the mail service.
 */
@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Mail API", description = "API for sending and tracking emails")
public class MailController {

    private final EmailService emailService;

    /**
     * Send an email.
     *
     * @param emailRequest the email request
     * @return the email status response
     */
    @PostMapping("/send")
    @Operation(summary = "Send an email", description = "Send an email with optional template and variables")
    public ResponseEntity<EmailStatusResponse> sendEmail(@Valid @RequestBody EmailRequest emailRequest) {
        log.info("Received request to send email to: {}", emailRequest.getTo());
        EmailStatusResponse response = emailService.sendEmail(emailRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get the status of an email.
     *
     * @param id the email ID
     * @return the email status response
     */
    @GetMapping("/status/{id}")
    @Operation(summary = "Get email status by ID", description = "Get the status of an email by its ID")
    public ResponseEntity<EmailStatusResponse> getEmailStatus(@PathVariable Long id) {
        log.info("Received request to get email status for ID: {}", id);
        EmailStatusResponse response = emailService.getEmailStatus(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get the status of an email by reference ID.
     *
     * @param referenceId the reference ID
     * @return the email status response
     */
    @GetMapping("/status/reference/{referenceId}")
    @Operation(summary = "Get email status by reference ID", description = "Get the status of an email by its reference ID")
    public ResponseEntity<EmailStatusResponse> getEmailStatusByReferenceId(@PathVariable String referenceId) {
        log.info("Received request to get email status for reference ID: {}", referenceId);
        EmailStatusResponse response = emailService.getEmailStatusByReferenceId(referenceId);
        return ResponseEntity.ok(response);
    }
} 