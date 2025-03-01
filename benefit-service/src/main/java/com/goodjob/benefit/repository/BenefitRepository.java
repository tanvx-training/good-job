package com.goodjob.benefit.repository;

import com.goodjob.benefit.entity.Benefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Benefit entity.
 */
@Repository
public interface BenefitRepository extends JpaRepository<Benefit, Integer> {

    /**
     * Find a benefit by its type.
     *
     * @param type the benefit type
     * @return the benefit if found
     */
    Optional<Benefit> findByType(String type);

    /**
     * Check if a benefit with the given type exists.
     *
     * @param type the benefit type
     * @return true if exists, false otherwise
     */
    boolean existsByType(String type);
} 