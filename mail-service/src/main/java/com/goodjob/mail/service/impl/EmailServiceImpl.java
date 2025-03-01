package com.goodjob.mail.service.impl;

import com.goodjob.mail.dto.EmailRequest;
import com.goodjob.mail.dto.EmailStatusResponse;
import com.goodjob.mail.exception.EmailNotFoundException;
import com.goodjob.mail.model.EmailLog;
import com.goodjob.mail.repository.EmailLogRepository;
import com.goodjob.mail.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * Implementation of the EmailService interface.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final EmailLogRepository emailLogRepository;
    private final TemplateEngine templateEngine;

    @Value("${mail-service.sender.name}")
    private String senderName;

    @Value("${mail-service.sender.email}")
    private String senderEmail;

    @Value("${mail-service.templates.path}")
    private String templatesPath;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public EmailStatusResponse sendEmail(EmailRequest emailRequest) {
        log.info("Sending email to: {}", emailRequest.getTo());

        // Create email log entry
        EmailLog emailLog = EmailLog.builder()
                .recipient(emailRequest.getTo())
                .subject(emailRequest.getSubject())
                .content(emailRequest.getBody())
                .templateName(emailRequest.getTemplateName())
                .sentAt(LocalDateTime.now())
                .status(EmailLog.EmailStatus.PENDING)
                .retryCount(0)
                .eventType(emailRequest.getEventType())
                .referenceId(emailRequest.getReferenceId() != null ? 
                        emailRequest.getReferenceId() : UUID.randomUUID().toString())
                .build();

        emailLogRepository.save(emailLog);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(senderEmail, senderName);
            helper.setTo(emailRequest.getTo());
            helper.setSubject(emailRequest.getSubject());

            // Use template if provided, otherwise use body
            if (emailRequest.getTemplateName() != null && !emailRequest.getTemplateName().isEmpty()) {
                String content = processTemplate(
                        emailRequest.getTemplateName(), 
                        emailRequest.getTemplateVariables()
                );
                helper.setText(content, true);
                emailLog.setContent(content);
            } else {
                helper.setText(emailRequest.getBody(), true);
            }

            mailSender.send(message);
            
            emailLog.setStatus(EmailLog.EmailStatus.SENT);
            emailLogRepository.save(emailLog);
            
            log.info("Email sent successfully to: {}", emailRequest.getTo());
        } catch (Exception e) {
            log.error("Failed to send email to: {}", emailRequest.getTo(), e);
            emailLog.setStatus(EmailLog.EmailStatus.FAILED);
            emailLog.setErrorMessage(e.getMessage());
            emailLogRepository.save(emailLog);
        }

        return EmailStatusResponse.fromEmailLog(emailLog);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public EmailStatusResponse getEmailStatus(Long id) {
        log.info("Getting email status for ID: {}", id);
        
        EmailLog emailLog = emailLogRepository.findById(id)
                .orElseThrow(() -> new EmailNotFoundException("Email not found with ID: " + id));
        
        return EmailStatusResponse.fromEmailLog(emailLog);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public EmailStatusResponse getEmailStatusByReferenceId(String referenceId) {
        log.info("Getting email status for reference ID: {}", referenceId);
        
        EmailLog emailLog = emailLogRepository.findByReferenceId(referenceId)
                .orElseThrow(() -> new EmailNotFoundException("Email not found with reference ID: " + referenceId));
        
        return EmailStatusResponse.fromEmailLog(emailLog);
    }

    /**
     * Process an email template with the given variables.
     *
     * @param templateName the template name
     * @param variables the template variables
     * @return the processed template as a string
     */
    private String processTemplate(String templateName, Map<String, Object> variables) {
        Context context = new Context();
        if (variables != null) {
            variables.forEach(context::setVariable);
        }
        
        return templateEngine.process(templateName, context);
    }
} 