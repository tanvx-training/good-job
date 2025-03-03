package com.goodjob.mail.service;

import com.goodjob.common.dto.ApiResponse;
import com.goodjob.common.exception.ResourceNotFoundException;
import com.goodjob.common.exception.ServiceException;
import com.goodjob.mail.dto.EmailRequest;
import com.goodjob.mail.dto.EmailStatusResponse;
import com.goodjob.mail.feign.CandidateServiceClient;
import com.goodjob.mail.feign.NotificationServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * Service interface for sending emails.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final CandidateServiceClient candidateServiceClient;
    private final NotificationServiceClient notificationServiceClient;

    @Value("${mail.from.address}")
    private String fromAddress;

    @Value("${mail.from.name}")
    private String fromName;

    /**
     * Send an email.
     *
     * @param emailRequest the email request
     * @return the email status response
     */
    public EmailStatusResponse sendEmail(EmailRequest emailRequest) {
        try {
            // Get candidate email from candidate service
            ApiResponse<String> response = candidateServiceClient.getCandidateEmail(emailRequest.getCandidateId());
            
            if (!response.isSuccess() || response.getData() == null) {
                throw new ResourceNotFoundException("Candidate", "id", emailRequest.getCandidateId());
            }
            
            String email = response.getData();
            
            // Send email
            boolean sent = sendEmail(email, emailRequest.getSubject(), emailRequest.getTemplateName(), emailRequest.getVariables());
            
            if (sent) {
                // Create notification
                createNotification(emailRequest.getCandidateId(), emailRequest.getSubject());
            }
            
            return new EmailStatusResponse(sent);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error sending email to candidate {}: {}", emailRequest.getCandidateId(), e.getMessage(), e);
            throw new ServiceException("Failed to send email to candidate: " + e.getMessage(), e);
        }
    }

    /**
     * Get the status of an email.
     *
     * @param id the email ID
     * @return the email status response
     */
    public EmailStatusResponse getEmailStatus(Long id) {
        // Implementation needed
        throw new UnsupportedOperationException("Method not implemented");
    }

    /**
     * Get the status of an email by reference ID.
     *
     * @param referenceId the reference ID
     * @return the email status response
     */
    public EmailStatusResponse getEmailStatusByReferenceId(String referenceId) {
        // Implementation needed
        throw new UnsupportedOperationException("Method not implemented");
    }

    /**
     * Sends an email to a candidate.
     *
     * @param candidateId the candidate ID
     * @param subject the email subject
     * @param templateName the template name
     * @param variables the template variables
     * @return true if the email was sent successfully
     */
    private boolean sendEmailToCandidate(Long candidateId, String subject, String templateName, Map<String, Object> variables) {
        try {
            // Get candidate email from candidate service
            ApiResponse<String> response = candidateServiceClient.getCandidateEmail(candidateId);
            
            if (!response.isSuccess() || response.getData() == null) {
                throw new ResourceNotFoundException("Candidate", "id", candidateId);
            }
            
            String email = response.getData();
            
            // Send email
            boolean sent = sendEmail(email, subject, templateName, variables);
            
            if (sent) {
                // Create notification
                createNotification(candidateId, subject);
            }
            
            return sent;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error sending email to candidate {}: {}", candidateId, e.getMessage(), e);
            throw new ServiceException("Failed to send email to candidate: " + e.getMessage(), e);
        }
    }

    /**
     * Sends an email.
     *
     * @param to the recipient email
     * @param subject the email subject
     * @param templateName the template name
     * @param variables the template variables
     * @return true if the email was sent successfully
     */
    private boolean sendEmail(String to, String subject, String templateName, Map<String, Object> variables) {
        try {
            // Process template
            Context context = new Context();
            variables.forEach(context::setVariable);
            String content = templateEngine.process(templateName, context);
            
            // Create message
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromAddress, fromName);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            
            // Send message
            mailSender.send(message);
            log.info("Email sent to {} with subject: {}", to, subject);
            
            return true;
        } catch (MessagingException e) {
            log.error("Error sending email to {}: {}", to, e.getMessage(), e);
            return false;
        } catch (Exception e) {
            log.error("Unexpected error sending email to {}: {}", to, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Creates a notification for a candidate.
     *
     * @param candidateId the candidate ID
     * @param subject the notification subject
     */
    private void createNotification(Long candidateId, String subject) {
        try {
            Map<String, Object> notification = new HashMap<>();
            notification.put("userId", candidateId.toString());
            notification.put("userType", "CANDIDATE");
            notification.put("type", "EMAIL");
            notification.put("title", "New Email");
            notification.put("message", "You have received a new email: " + subject);
            
            notificationServiceClient.createNotification(notification);
            log.info("Notification created for candidate {}", candidateId);
        } catch (Exception e) {
            log.warn("Failed to create notification for candidate {}: {}", candidateId, e.getMessage());
            // Don't throw exception, as this is a secondary operation
        }
    }
} 