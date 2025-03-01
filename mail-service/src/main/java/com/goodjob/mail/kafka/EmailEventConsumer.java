package com.goodjob.mail.kafka;

import com.goodjob.mail.dto.EmailEvent;
import com.goodjob.mail.dto.EmailRequest;
import com.goodjob.mail.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka consumer for email events.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class EmailEventConsumer {

    private final EmailService emailService;

    @Value("${mail-service.kafka.topics.email-events}")
    private String emailEventsTopic;

    /**
     * Listen for email events.
     *
     * @param emailEvent the email event
     */
    @KafkaListener(topics = "${mail-service.kafka.topics.email-events}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeEmailEvent(EmailEvent emailEvent) {
        log.info("Received email event: {}", emailEvent);

        try {
            EmailRequest emailRequest = EmailRequest.builder()
                    .to(emailEvent.getTo())
                    .subject(emailEvent.getSubject())
                    .templateName(emailEvent.getTemplateName())
                    .templateVariables(emailEvent.getTemplateVariables())
                    .eventType(emailEvent.getEventType())
                    .referenceId(emailEvent.getReferenceId())
                    .build();

            emailService.sendEmail(emailRequest);
            log.info("Email sent successfully for event: {}", emailEvent.getEventType());
        } catch (Exception e) {
            log.error("Failed to process email event: {}", emailEvent, e);
        }
    }
} 