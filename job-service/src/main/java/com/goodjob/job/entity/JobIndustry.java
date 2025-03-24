package com.goodjob.job.entity;

import com.goodjob.common.entity.BaseEntity;
import com.goodjob.job.entity.id.JobIndustryId;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Entity representing an industry associated with a job posting.
 */
@Table(name = "job_industries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class JobIndustry extends BaseEntity {

    @EmbeddedId
    private JobIndustryId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("jobId")
    @JoinColumn(name = "job_id")
    private Job job;

    public Integer getIndustryId() {
        return id.getIndustryId();
    }
} 