package com.goodjob.profile.domain.profile.repository;

import com.goodjob.profile.domain.profile.entity.Profile;
import com.goodjob.profile.domain.profile.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Project entity.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    List<Project> findByProfile(Profile profile);
    
    List<Project> findByProfileProfileId(Long profileId);
    
    void deleteByProfileProfileId(Long profileId);
} 