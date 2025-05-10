package com.goodjob.profile.domain.profile.repository;

import com.goodjob.profile.domain.profile.entity.Certification;
import com.goodjob.profile.domain.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Certification entity.
 */
@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {
    
    List<Certification> findByProfile(Profile profile);
    
    List<Certification> findByProfileProfileId(Long profileId);
    
    void deleteByProfileProfileId(Long profileId);
} 