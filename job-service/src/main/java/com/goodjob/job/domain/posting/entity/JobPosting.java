package com.goodjob.job.domain.posting.entity;

import com.goodjob.common.entity.BaseEntity;
import com.goodjob.job.domain.job.entity.Job;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Entity representing a job posting.
 */
@Table(name = "job_postings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class JobPosting extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "posting_id")
    private Long postingId;

    @Column(name = "job_id", nullable = false)
    private Long jobId;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "views", nullable = false)
    private Integer views;

    @Column(name = "applies", nullable = false)
    private Integer applies;

    @Column(name = "posting_domain", nullable = false)
    private String postingDomain;

    @Column(name = "job_posting_url", nullable = false, columnDefinition = "TEXT")
    private String jobPostingUrl;

    @Column(name = "application_url", nullable = false, columnDefinition = "TEXT")
    private String applicationUrl;

    @Column(name = "application_type", nullable = false)
    private String applicationType;

    @Column(name = "sponsored")
    private boolean sponsored;

    @Column(name = "fips", length = 50)
    private String fips;

    @Column(name = "posting_status", nullable = false)
    private Integer postingStatus;

    @Column(name = "original_listed_time")
    private Long originalListedTime;

    @Column(name = "listed_time")
    private Long listedTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;
} 