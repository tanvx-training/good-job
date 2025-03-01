package com.goodjob.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * DTO for email events sent via Kafka.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailEvent {

    private String to;
    private String subject;
    private String templateName;
    private Map<String, Object> templateVariables;
    private String eventType;
    private String referenceId;
} 