package com.goodjob.profile.domain.profile.repository;

import com.goodjob.profile.domain.profile.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Experience entity.
 */
@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
} 