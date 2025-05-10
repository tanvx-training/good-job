package com.goodjob.profile.domain.profile.query;

import com.goodjob.common.application.dto.response.PageResponseDTO;
import com.goodjob.profile.domain.profile.dto.*;

import java.util.List;

/**
 * Service for handling profile queries.
 */
public interface ProfileQueryService {

    ProfileDTO getProfileById(Long profileId);
    
    ProfileDTO getProfileByUserId(String userId);
    
    PageResponseDTO<ProfileDTO> searchProfiles(ProfileSearchQuery query);
    
    ExperienceDTO getExperienceById(Long experienceId);
    
    List<ExperienceDTO> getExperiencesByProfileId(Long profileId);
    
    EducationDTO getEducationById(Long educationId);
    
    List<EducationDTO> getEducationsByProfileId(Long profileId);
    
    List<SkillDTO> getSkillsByProfileId(Long profileId);
    
    CertificationDTO getCertificationById(Long certificationId);
    
    List<CertificationDTO> getCertificationsByProfileId(Long profileId);
    
    ProjectDTO getProjectById(Long projectId);
    
    List<ProjectDTO> getProjectsByProfileId(Long profileId);
    
    List<JobRecommendationDTO> getJobRecommendations(Long profileId);
} 