package com.goodjob.profile.domain.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for profile data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
    
    private Long profileId;
    private String userId;
    private String firstName;
    private String lastName;
    private String headline;
    private String summary;
    private String email;
    private String phone;
    private String location;
    private String profileImageUrl;
    private Integer profileStatus;
    private List<ExperienceDTO> experiences;
    private List<EducationDTO> educations;
    private List<SkillDTO> skills;
    private List<CertificationDTO> certifications;
    private List<ProjectDTO> projects;
} 