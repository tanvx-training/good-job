package com.goodjob.profile.domain.profile.repository;

import com.goodjob.profile.domain.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Repository for Profile entity.
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long>,
        JpaSpecificationExecutor<Profile> {
} 