package com.goodjob.job.domain.job.entity;

import com.goodjob.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

/**
 * Entity representing a job posting.
 */
@Table(name = "jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Job extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "company_id", nullable = false)
    private Integer companyId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "work_type")
    private Integer workType;

    @Column(name = "education_level")
    private Integer educationLevel;

    @Column(name = "experience_level")
    private Integer experienceLevel;

    @Column(name = "remote_allowed", nullable = false)
    private Boolean remoteAllowed;

    @Column(name = "location")
    private String location;

    @Column(name = "zip_code", length = 50)
    private String zipCode;

    @Column(name = "skills_desc", columnDefinition = "TEXT")
    private String skillsDesc;

    @Column(name = "expiry")
    private Long expiry;

    @Column(name = "closed_time")
    private Long closedTime;

    @Column(name = "job_status", nullable = false)
    private Integer jobStatus;

    @Column(name = "views", nullable = false)
    private Integer views;

    @Column(name = "applies", nullable = false)
    private Integer applies;

    @OneToOne(mappedBy = "job")
    private JobSalary jobSalary;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<JobBenefit> jobBenefits;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<JobSkill> jobSkills;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<JobIndustry> jobIndustries;
} 