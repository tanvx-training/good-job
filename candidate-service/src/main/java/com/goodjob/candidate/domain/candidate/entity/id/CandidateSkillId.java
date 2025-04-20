package com.goodjob.candidate.domain.candidate.entity.id;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class CandidateSkillId {

    private Integer candidateId;
    private Integer skillId;
}
