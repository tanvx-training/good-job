package com.goodjob.job.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing an industry associated with a job posting.
 */
@Entity
@Table(name = "job_industries")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "job")
@ToString(exclude = "job")
public class JobIndustry {

    @EmbeddedId
    private JobIndustryId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("jobId")
    @JoinColumn(name = "job_id")
    private Job job;
} 