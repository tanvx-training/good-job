package com.goodjob.posting.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Entity representing a job application.
 */
@Entity
@Table(name = "job_applications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"jobPosting"})
@ToString(exclude = {"jobPosting"})
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_applications_id_seq")
    @SequenceGenerator(name = "job_applications_id_seq", sequenceName = "job_applications_id_seq", allocationSize = 1)
    @Column(name = "application_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posting_id", nullable = false)
    private JobPosting jobPosting;

    @Column(name = "applicant_id", nullable = false)
    private String applicantId;

    @Column(name = "resume_url")
    private String resumeUrl;

    @Column(name = "cover_letter", columnDefinition = "TEXT")
    private String coverLetter;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "employer_viewed")
    private Boolean employerViewed;

    @Column(name = "applicant_viewed")
    private Boolean applicantViewed;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = ApplicationStatus.PENDING;
        }
        if (employerViewed == null) {
            employerViewed = false;
        }
        if (applicantViewed == null) {
            applicantViewed = false;
        }
    }

    /**
     * Mark the application as viewed by the employer.
     */
    public void markViewedByEmployer() {
        this.employerViewed = true;
    }

    /**
     * Mark the application as viewed by the applicant.
     */
    public void markViewedByApplicant() {
        this.applicantViewed = true;
    }

    /**
     * Update the status of the application.
     *
     * @param status the new status
     */
    public void updateStatus(ApplicationStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }
} 