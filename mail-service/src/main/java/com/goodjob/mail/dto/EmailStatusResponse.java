package com.goodjob.mail.dto;

import com.goodjob.mail.model.EmailLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for email status responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailStatusResponse {

    private Long id;
    private String recipient;
    private String subject;
    private String status;
    private LocalDateTime sentAt;
    private String eventType;
    private String referenceId;

    /**
     * Convert an EmailLog entity to an EmailStatusResponse DTO.
     *
     * @param emailLog the email log entity
     * @return the email status response DTO
     */
    public static EmailStatusResponse fromEmailLog(EmailLog emailLog) {
        return EmailStatusResponse.builder()
                .id(emailLog.getId())
                .recipient(emailLog.getRecipient())
                .subject(emailLog.getSubject())
                .status(emailLog.getStatus().name())
                .sentAt(emailLog.getSentAt())
                .eventType(emailLog.getEventType())
                .referenceId(emailLog.getReferenceId())
                .build();
    }
} 