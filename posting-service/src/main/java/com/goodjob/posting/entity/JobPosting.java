package com.goodjob.posting.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a job posting.
 */
@Entity
@Table(name = "job_postings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"applications"})
@ToString(exclude = {"applications"})
public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_postings_id_seq")
    @SequenceGenerator(name = "job_postings_id_seq", sequenceName = "job_postings_id_seq", allocationSize = 1)
    @Column(name = "posting_id")
    private Integer id;

    @Column(name = "job_id", nullable = false)
    private Integer jobId;

    @Column(name = "employer_id", nullable = false)
    private String employerId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "job_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @Column(name = "experience_level")
    @Enumerated(EnumType.STRING)
    private ExperienceLevel experienceLevel;

    @Column(name = "education_level")
    @Enumerated(EnumType.STRING)
    private EducationLevel educationLevel;

    @Column(name = "min_salary")
    private BigDecimal minSalary;

    @Column(name = "max_salary")
    private BigDecimal maxSalary;

    @Column(name = "currency")
    private String currency;

    @Column(name = "salary_period")
    @Enumerated(EnumType.STRING)
    private SalaryPeriod salaryPeriod;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PostingStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "views")
    private Integer views;

    @Column(name = "applications")
    private Integer applicationCount;

    @OneToMany(mappedBy = "jobPosting", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<JobApplication> applications = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (views == null) {
            views = 0;
        }
        if (applicationCount == null) {
            applicationCount = 0;
        }
        if (status == null) {
            status = PostingStatus.ACTIVE;
        }
        if (currency == null) {
            currency = "USD";
        }
        if (salaryPeriod == null) {
            salaryPeriod = SalaryPeriod.YEARLY;
        }
    }

    /**
     * Add an application to the job posting.
     *
     * @param application the application to add
     */
    public void addApplication(JobApplication application) {
        applications.add(application);
        application.setJobPosting(this);
        applicationCount++;
    }

    /**
     * Remove an application from the job posting.
     *
     * @param application the application to remove
     */
    public void removeApplication(JobApplication application) {
        applications.remove(application);
        application.setJobPosting(null);
        applicationCount = Math.max(0, applicationCount - 1);
    }

    /**
     * Increment the view count of the job posting.
     *
     * @return the new view count
     */
    public Integer incrementViews() {
        views++;
        return views;
    }
} 