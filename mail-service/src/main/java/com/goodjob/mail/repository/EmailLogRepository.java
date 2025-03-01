package com.goodjob.mail.repository;

import com.goodjob.mail.model.EmailLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for the EmailLog entity.
 */
@Repository
public interface EmailLogRepository extends JpaRepository<EmailLog, Long> {

    /**
     * Find email logs by recipient.
     *
     * @param recipient the recipient email address
     * @return a list of email logs
     */
    List<EmailLog> findByRecipientOrderBySentAtDesc(String recipient);

    /**
     * Find email logs by status.
     *
     * @param status the email status
     * @return a list of email logs
     */
    List<EmailLog> findByStatus(EmailLog.EmailStatus status);

    /**
     * Find email logs by reference ID.
     *
     * @param referenceId the reference ID
     * @return an optional email log
     */
    Optional<EmailLog> findByReferenceId(String referenceId);

    /**
     * Find email logs by event type.
     *
     * @param eventType the event type
     * @return a list of email logs
     */
    List<EmailLog> findByEventType(String eventType);
} 