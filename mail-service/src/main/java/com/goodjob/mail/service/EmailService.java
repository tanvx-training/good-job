package com.goodjob.mail.service;

import com.goodjob.mail.dto.EmailRequest;
import com.goodjob.mail.dto.EmailStatusResponse;

/**
 * Service interface for sending emails.
 */
public interface EmailService {

    /**
     * Send an email.
     *
     * @param emailRequest the email request
     * @return the email status response
     */
    EmailStatusResponse sendEmail(EmailRequest emailRequest);

    /**
     * Get the status of an email.
     *
     * @param id the email ID
     * @return the email status response
     */
    EmailStatusResponse getEmailStatus(Long id);

    /**
     * Get the status of an email by reference ID.
     *
     * @param referenceId the reference ID
     * @return the email status response
     */
    EmailStatusResponse getEmailStatusByReferenceId(String referenceId);
} 