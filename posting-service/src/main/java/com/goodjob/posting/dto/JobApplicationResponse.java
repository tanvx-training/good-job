package com.goodjob.posting.dto;

import com.goodjob.posting.entity.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for returning job application information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationResponse {

    private Integer id;
    private Integer postingId;
    private String applicantId;
    private String resumeUrl;
    private String coverLetter;
    private ApplicationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean employerViewed;
    private Boolean applicantViewed;
    private JobPostingResponse jobPosting;
} 