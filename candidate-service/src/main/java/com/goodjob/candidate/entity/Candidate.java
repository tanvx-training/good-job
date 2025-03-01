package com.goodjob.candidate.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
@Builder
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String userId;

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

    @Column(length = 255)
    private String profilePictureUrl;

    @Column(length = 255)
    private String resumeUrl;

    private LocalDate dateOfBirth;

    @Column(length = 50)
    private String currentJobTitle;

    @Column(length = 100)
    private String currentCompany;

    @Column(length = 100)
    private String highestEducation;

    @Column(length = 100)
    private String educationInstitution;

    @Column(length = 255)
    private String linkedinUrl;

    @Column(length = 255)
    private String githubUrl;

    @Column(length = 255)
    private String portfolioUrl;

    @Column(nullable = false)
    private boolean openToWork;

    @Column(nullable = false)
    private boolean openToRelocate;

    @Column(nullable = false)
    private boolean profileCompleted;

    @Column(nullable = false)
    private boolean profileVisible;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CandidateSkill> skills = new HashSet<>();

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CandidateExperience> experiences = new HashSet<>();

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CandidateEducation> educations = new HashSet<>();

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CandidateProject> projects = new HashSet<>();

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CandidateCertification> certifications = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /**
     * Returns the full name of the candidate.
     *
     * @return the full name (first name + last name)
     */
    @Transient
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Adds a skill to the candidate's skill set.
     *
     * @param skill the skill to add
     */
    public void addSkill(CandidateSkill skill) {
        skills.add(skill);
        skill.setCandidate(this);
    }

    /**
     * Removes a skill from the candidate's skill set.
     *
     * @param skill the skill to remove
     */
    public void removeSkill(CandidateSkill skill) {
        skills.remove(skill);
        skill.setCandidate(null);
    }

    /**
     * Adds an experience to the candidate's experience set.
     *
     * @param experience the experience to add
     */
    public void addExperience(CandidateExperience experience) {
        experiences.add(experience);
        experience.setCandidate(this);
    }

    /**
     * Removes an experience from the candidate's experience set.
     *
     * @param experience the experience to remove
     */
    public void removeExperience(CandidateExperience experience) {
        experiences.remove(experience);
        experience.setCandidate(null);
    }

    /**
     * Adds an education to the candidate's education set.
     *
     * @param education the education to add
     */
    public void addEducation(CandidateEducation education) {
        educations.add(education);
        education.setCandidate(this);
    }

    /**
     * Removes an education from the candidate's education set.
     *
     * @param education the education to remove
     */
    public void removeEducation(CandidateEducation education) {
        educations.remove(education);
        education.setCandidate(null);
    }

    /**
     * Adds a project to the candidate's project set.
     *
     * @param project the project to add
     */
    public void addProject(CandidateProject project) {
        projects.add(project);
        project.setCandidate(this);
    }

    /**
     * Removes a project from the candidate's project set.
     *
     * @param project the project to remove
     */
    public void removeProject(CandidateProject project) {
        projects.remove(project);
        project.setCandidate(null);
    }

    /**
     * Adds a certification to the candidate's certification set.
     *
     * @param certification the certification to add
     */
    public void addCertification(CandidateCertification certification) {
        certifications.add(certification);
        certification.setCandidate(this);
    }

    /**
     * Removes a certification from the candidate's certification set.
     *
     * @param certification the certification to remove
     */
    public void removeCertification(CandidateCertification certification) {
        certifications.remove(certification);
        certification.setCandidate(null);
    }
} 