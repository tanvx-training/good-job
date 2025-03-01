package com.goodjob.job.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a skill required for a job posting.
 */
@Entity
@Table(name = "job_skills")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "job")
@ToString(exclude = "job")
public class JobSkill {

    @EmbeddedId
    private JobSkillId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("jobId")
    @JoinColumn(name = "job_id")
    private Job job;

    @Column(name = "skill_level")
    @Enumerated(EnumType.STRING)
    private SkillLevel skillLevel;

    @Column(name = "is_required")
    private Boolean isRequired;

    @PrePersist
    protected void onCreate() {
        if (isRequired == null) {
            isRequired = true;
        }
        if (skillLevel == null) {
            skillLevel = SkillLevel.INTERMEDIATE;
        }
    }
}