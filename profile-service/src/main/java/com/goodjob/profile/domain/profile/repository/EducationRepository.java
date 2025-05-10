package com.goodjob.profile.domain.profile.repository;

import com.goodjob.profile.domain.profile.entity.Education;
import com.goodjob.profile.domain.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Education entity.
 */
@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
    
    List<Education> findByProfile(Profile profile);
    
    List<Education> findByProfileProfileId(Long profileId);
    
    void deleteByProfileProfileId(Long profileId);
} 