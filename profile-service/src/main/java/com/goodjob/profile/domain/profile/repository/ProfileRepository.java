package com.goodjob.profile.domain.profile.repository;

import com.goodjob.profile.domain.profile.entity.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Profile entity.
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    
    Optional<Profile> findByUserId(String userId);
    
    boolean existsByUserId(String userId);
    
    Page<Profile> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName, Pageable pageable);
} 