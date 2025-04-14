package com.goodjob.job.domain.posting.entity;

import com.goodjob.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(name = "job_posting_url", nullable = false, columnDefinition = "TEXT")
    private String jobPostingUrl;

    @Column(name = "posting_status", nullable = false)
    private Integer postingStatus;
} 