package com.goodjob.profile.domain.profile.entity;

import com.goodjob.common.domain.entity.BaseEntity;
import com.goodjob.profile.domain.profile.entity.id.ProfileSkillId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Entity representing a profile's skill with proficiency level.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "profile_skills")
public class ProfileSkill extends BaseEntity {

    @EmbeddedId
    private ProfileSkillId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @Column(name = "proficiency_level")
    private Integer proficiencyLevel; // 1-5 scale
} 