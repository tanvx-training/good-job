package com.goodjob.speciality.repository;

import com.goodjob.speciality.entity.Speciality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Speciality entity.
 */
@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Integer> {

    /**
     * Find a speciality by its name.
     *
     * @param name the speciality name
     * @return an Optional containing the speciality if found, or empty if not found
     */
    Optional<Speciality> findBySpecialityName(String name);

    /**
     * Check if a speciality exists by its name.
     *
     * @param name the speciality name
     * @return true if the speciality exists, false otherwise
     */
    boolean existsBySpecialityName(String name);

    Page<Speciality> findAllByDeleteFlg(Boolean deleteFlg, Pageable pageable);
} 