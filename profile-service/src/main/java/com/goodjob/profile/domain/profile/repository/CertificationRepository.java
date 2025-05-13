package com.goodjob.profile.domain.profile.repository;

import com.goodjob.profile.domain.profile.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Certification entity.
 */
@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {
} 