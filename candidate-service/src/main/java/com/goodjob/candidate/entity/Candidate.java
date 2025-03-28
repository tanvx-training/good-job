package com.goodjob.candidate.entity;

import com.goodjob.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a candidate in the job platform.
 * Contains personal information, contact details, and professional information.
 */
@Entity
@Table(name = "candidates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Candidate extends BaseEntity {

    @Id
    @Column(name = "candidate_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer candidateId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(length = 500)
    private String headline;

    @Column(length = 5000)
    private String summary;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(length = 20)
    private String phone;

    @Column(length = 100)
    private String location;

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    @Column(name = "resume_url")
    private String resumeUrl;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(length = 50, name = "current_job_title")
    private String currentJobTitle;

    @Column(length = 100, name = "current_company")
    private String currentCompany;

    @Column(length = 100, name = "highest_education")
    private String highestEducation;

    @Column(length = 100, name = "education_institution")
    private String educationInstitution;

    @Column(name = "linkedin_url")
    private String linkedinUrl;

    @Column(name = "github_url")
    private String githubUrl;

    @Column(name = "portfolio_url")
    private String portfolioUrl;

    @Column(nullable = false)
    private boolean openToWork;

    @Column(nullable = false)
    private boolean openToRelocate;

    @Column(nullable = false)
    private boolean profileCompleted;

    @Column(nullable = false)
    private boolean profileVisible;

    @OneToMany(mappedBy = "candidate")
    private Set<CandidateSkill> candidateSkills;

    @OneToMany(mappedBy = "candidate")
    private Set<CandidateExperience> candidateExperiences;

    @OneToMany(mappedBy = "candidate")
    private Set<CandidateEducation> candidateEducations;

    @OneToMany(mappedBy = "candidate")
    private Set<CandidateProject> candidateProjects;

    @OneToMany(mappedBy = "candidate")
    private Set<CandidateCertification> candidateCertifications;
} 