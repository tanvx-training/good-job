package com.goodjob.job.domain.job.entity;

import com.goodjob.common.domain.entity.BaseEntity;
import com.goodjob.job.domain.job.entity.id.JobSkillId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Entity representing a skill required for a job posting.
 */
@Table(name = "job_skills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class JobSkill extends BaseEntity {

    @EmbeddedId
    private JobSkillId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("jobId")
    @JoinColumn(name = "job_id")
    private Job job;

    public Integer getSkillId() {
        return id.getSkillId();
    }
}