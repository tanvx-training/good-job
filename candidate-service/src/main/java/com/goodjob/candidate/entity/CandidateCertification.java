package com.goodjob.candidate.entity;

import com.goodjob.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * Entity representing a certification associated with a candidate.
 * Contains information about the certification name, issuing organization, and validity.
 */
@Entity
@Table(name = "candidate_certifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CandidateCertification extends BaseEntity {

    @Id
    @Column(name = "candidate_certification_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer candidateCertificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String issuingOrganization;

    @Column(length = 100)
    private String credentialId;

    @Column(length = 255)
    private String credentialUrl;

    private LocalDate issueDate;

    private LocalDate expirationDate;

    @Column(nullable = false)
    private boolean doesNotExpire;
} 