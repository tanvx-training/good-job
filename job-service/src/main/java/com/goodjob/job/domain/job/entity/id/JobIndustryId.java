package com.goodjob.job.domain.job.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Composite key for the JobIndustry entity.
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobIndustryId implements Serializable {

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "industry_id")
    private Integer industryId;
}