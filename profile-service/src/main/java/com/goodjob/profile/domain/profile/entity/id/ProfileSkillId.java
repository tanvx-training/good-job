package com.goodjob.profile.domain.profile.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileSkillId implements Serializable {

    @Column(name = "profile_id")
    private Long profileId;

    @Column(name = "skill_id")
    private Integer skillId;
}
