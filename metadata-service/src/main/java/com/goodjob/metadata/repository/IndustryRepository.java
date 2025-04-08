package com.goodjob.metadata.repository;

import com.goodjob.metadata.entity.Industry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Industry entity.
 */
@Repository
public interface IndustryRepository extends JpaRepository<Industry, Integer> {

    /**
     * Find an industry by its name.
     *
     * @param name the industry name
     * @return the industry if found
     */
    Optional<Industry> findByIndustryName(String name);

    /**
     * Check if an industry with the given name exists.
     *
     * @param name the industry name
     * @return true if exists, false otherwise
     */
    boolean existsByIndustryName(String name);

    Page<Industry> findAllByDeleteFlg(boolean deleteFlg, Pageable pageable);
} 