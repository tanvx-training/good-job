package com.goodjob.profile.domain.profile.repository;

import com.goodjob.profile.domain.profile.entity.Experience;
import com.goodjob.profile.domain.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Experience entity.
 */
@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    
    List<Experience> findByProfile(Profile profile);
    
    List<Experience> findByProfileProfileId(Long profileId);
    
    void deleteByProfileProfileId(Long profileId);
} 