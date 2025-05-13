package com.goodjob.profile.domain.profile.repository;

import com.goodjob.profile.domain.profile.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Education entity.
 */
@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
} 