package com.goodjob.candidate.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a skill associated with a candidate.
 * Contains information about the skill name, proficiency level, and years of experience.
 */
@Entity
@Table(name = "candidate_skills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @Column(nullable = false, length = 100)
    private String skillName;

    @Column(nullable = false)
    private Integer proficiencyLevel;

    private Integer yearsOfExperience;

    @Column(length = 500)
    private String description;
} 