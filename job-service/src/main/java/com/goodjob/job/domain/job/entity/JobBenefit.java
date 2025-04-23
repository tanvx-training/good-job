package com.goodjob.job.domain.job.entity;

import com.goodjob.common.domain.entity.BaseEntity;
import com.goodjob.job.domain.job.entity.id.JobBenefitId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Entity representing an industry associated with a job posting.
 */
@Table(name = "job_benefits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class JobBenefit extends BaseEntity {

    @EmbeddedId
    private JobBenefitId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("jobId")
    @JoinColumn(name = "job_id")
    private Job job;

    public Integer getBenefitId() {
        return id.getBenefitId();
    }
} 