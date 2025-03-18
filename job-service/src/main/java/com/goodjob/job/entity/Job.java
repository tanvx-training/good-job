package com.goodjob.job.entity;

import com.goodjob.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "min_salary")
    private BigDecimal minSalary;

    @Column(name = "max_salary")
    private BigDecimal maxSalary;

    @Column(name = "med_salary")
    private BigDecimal medSalary;

    @Column(name = "pay_period", length = 50)
    private String payPeriod;

    @Column(name = "work_type", length = 100)
    private String workType;

    @Column(name = "formatted_experience_level", length = 100)
    private String formattedExperienceLevel;

    @Column(name = "remote_allowed", nullable = false)
    private boolean remoteAllowed;

    @Column(name = "location")
    private String location;

    @Column(name = "zip_code", length = 50)
    private String zipCode;

    @Column(name = "currency", length = 10)
    private String currency;

    @Column(name = "compensation_type", length = 100)
    private String compensationType;

    @Column(name = "skills_desc", columnDefinition = "TEXT")
    private String skillsDesc;

    @Column(name = "expiry")
    private Long expiry;

    @Column(name = "closed_time")
    private Long closedTime;
} 