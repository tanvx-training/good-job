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
} 