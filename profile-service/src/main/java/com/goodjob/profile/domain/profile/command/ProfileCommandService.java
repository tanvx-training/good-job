package com.goodjob.profile.domain.profile.command;

import com.goodjob.profile.domain.profile.dto.*;

/**
 * Service for handling profile commands.
 */
public interface ProfileCommandService {

    ProfileDTO createProfile(ProfileDTO profileDTO);
    
    ProfileDTO updateProfile(Long profileId, ProfileDTO profileDTO);
    
    void deleteProfile(Long profileId);
    
    ExperienceDTO addExperience(Long profileId, ExperienceDTO experienceDTO);
    
    ExperienceDTO updateExperience(Long experienceId, ExperienceDTO experienceDTO);
    
    void deleteExperience(Long experienceId);
    
    EducationDTO addEducation(Long profileId, EducationDTO educationDTO);
    
    EducationDTO updateEducation(Long educationId, EducationDTO educationDTO);
    
    void deleteEducation(Long educationId);
    
    SkillDTO addSkill(Long profileId, SkillDTO skillDTO);
    
    SkillDTO updateSkill(Long profileSkillId, SkillDTO skillDTO);
    
    void deleteSkill(Long profileSkillId);
    
    CertificationDTO addCertification(Long profileId, CertificationDTO certificationDTO);
    
    CertificationDTO updateCertification(Long certificationId, CertificationDTO certificationDTO);
    
    void deleteCertification(Long certificationId);
    
    ProjectDTO addProject(Long profileId, ProjectDTO projectDTO);
    
    ProjectDTO updateProject(Long projectId, ProjectDTO projectDTO);
    
    void deleteProject(Long projectId);
    
    void updateProfileStatus(Long profileId, Integer status);
} 