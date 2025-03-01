package com.goodjob.job.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a job posting.
 */
@Entity
@Table(name = "jobs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"salary", "skills", "industries"})
@ToString(exclude = {"salary", "skills", "industries"})
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jobs_job_id_seq")
    @SequenceGenerator(name = "jobs_job_id_seq", sequenceName = "jobs_job_id_seq", allocationSize = 1)
    @Column(name = "job_id")
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "requirements", columnDefinition = "TEXT")
    private String requirements;

    @Column(name = "employer_id", nullable = false)
    private String employerId;

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

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private JobStatus status;

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
    private Integer applications;

    @Column(name = "search_vector", columnDefinition = "jsonb")
    private String searchVector;

    @OneToOne(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private JobSalary salary;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<JobSkill> skills = new HashSet<>();

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<JobIndustry> industries = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (views == null) {
            views = 0;
        }
        if (applications == null) {
            applications = 0;
        }
        if (status == null) {
            status = JobStatus.ACTIVE;
        }
    }

    /**
     * Add a skill to the job.
     *
     * @param skill the skill to add
     */
    public void addSkill(JobSkill skill) {
        skills.add(skill);
        skill.setJob(this);
    }

    /**
     * Remove a skill from the job.
     *
     * @param skill the skill to remove
     */
    public void removeSkill(JobSkill skill) {
        skills.remove(skill);
        skill.setJob(null);
    }

    /**
     * Add an industry to the job.
     *
     * @param industry the industry to add
     */
    public void addIndustry(JobIndustry industry) {
        industries.add(industry);
        industry.setJob(this);
    }

    /**
     * Remove an industry from the job.
     *
     * @param industry the industry to remove
     */
    public void removeIndustry(JobIndustry industry) {
        industries.remove(industry);
        industry.setJob(null);
    }

    /**
     * Set the salary for the job.
     *
     * @param salary the salary to set
     */
    public void setSalary(JobSalary salary) {
        this.salary = salary;
        if (salary != null) {
            salary.setJob(this);
        }
    }
} 