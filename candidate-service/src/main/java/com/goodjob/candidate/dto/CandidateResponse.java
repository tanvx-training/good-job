package com.goodjob.candidate.dto;

import com.goodjob.candidate.entity.Candidate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Transfer Object for returning candidate data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateResponse {

    private Integer id;
    private String userId;
    private String firstName;
    private String lastName;
    private String fullName;
    private String headline;
    private String summary;
    private String email;
    private String phone;
    private String location;
    private String profilePictureUrl;
    private String resumeUrl;
    private LocalDate dateOfBirth;
    private String currentJobTitle;
    private String currentCompany;
    private String highestEducation;
    private String educationInstitution;
    private String linkedinUrl;
    private String githubUrl;
    private String portfolioUrl;
    private boolean openToWork;
    private boolean openToRelocate;
    private boolean profileCompleted;
    private boolean profileVisible;
    private List<CandidateSkillResponse> skills;
    private List<CandidateExperienceResponse> experiences;
    private List<CandidateEducationResponse> educations;
    private List<CandidateProjectResponse> projects;
    private List<CandidateCertificationResponse> certifications;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Converts a Candidate entity to a CandidateResponse DTO.
     *
     * @param candidate the Candidate entity
     * @return the CandidateResponse DTO
     */
    public static CandidateResponse fromEntity(Candidate candidate) {
        CandidateResponse response = CandidateResponse.builder()
                .id(candidate.getId())
                .userId(candidate.getUserId())
                .firstName(candidate.getFirstName())
                .lastName(candidate.getLastName())
                .fullName(candidate.getFullName())
                .headline(candidate.getHeadline())
                .summary(candidate.getSummary())
                .email(candidate.getEmail())
                .phone(candidate.getPhone())
                .location(candidate.getLocation())
                .profilePictureUrl(candidate.getProfilePictureUrl())
                .resumeUrl(candidate.getResumeUrl())
                .dateOfBirth(candidate.getDateOfBirth())
                .currentJobTitle(candidate.getCurrentJobTitle())
                .currentCompany(candidate.getCurrentCompany())
                .highestEducation(candidate.getHighestEducation())
                .educationInstitution(candidate.getEducationInstitution())
                .linkedinUrl(candidate.getLinkedinUrl())
                .githubUrl(candidate.getGithubUrl())
                .portfolioUrl(candidate.getPortfolioUrl())
                .openToWork(candidate.isOpenToWork())
                .openToRelocate(candidate.isOpenToRelocate())
                .profileCompleted(candidate.isProfileCompleted())
                .profileVisible(candidate.isProfileVisible())
                .createdAt(candidate.getCreatedAt())
                .updatedAt(candidate.getUpdatedAt())
                .build();

        if (candidate.getSkills() != null && !candidate.getSkills().isEmpty()) {
            response.setSkills(candidate.getSkills().stream()
                    .map(CandidateSkillResponse::fromEntity)
                    .collect(Collectors.toList()));
        }

        if (candidate.getExperiences() != null && !candidate.getExperiences().isEmpty()) {
            response.setExperiences(candidate.getExperiences().stream()
                    .map(CandidateExperienceResponse::fromEntity)
                    .collect(Collectors.toList()));
        }

        if (candidate.getEducations() != null && !candidate.getEducations().isEmpty()) {
            response.setEducations(candidate.getEducations().stream()
                    .map(CandidateEducationResponse::fromEntity)
                    .collect(Collectors.toList()));
        }

        if (candidate.getProjects() != null && !candidate.getProjects().isEmpty()) {
            response.setProjects(candidate.getProjects().stream()
                    .map(CandidateProjectResponse::fromEntity)
                    .collect(Collectors.toList()));
        }

        if (candidate.getCertifications() != null && !candidate.getCertifications().isEmpty()) {
            response.setCertifications(candidate.getCertifications().stream()
                    .map(CandidateCertificationResponse::fromEntity)
                    .collect(Collectors.toList()));
        }

        return response;
    }
} 