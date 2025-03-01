package com.goodjob.candidate.repository;

import com.goodjob.candidate.entity.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Candidate entity.
 */
@Repository
public interface CandidateRepository extends JpaRepository<Candidate, String> {

    /**
     * Finds a candidate by user ID.
     *
     * @param userId the user ID
     * @return the optional candidate
     */
    Optional<Candidate> findByUserId(String userId);

    /**
     * Finds a candidate by email.
     *
     * @param email the email address
     * @return an Optional containing the candidate if found, empty otherwise
     */
    Optional<Candidate> findByEmail(String email);

    /**
     * Checks if a candidate exists by user ID.
     *
     * @param userId the user ID
     * @return true if the candidate exists, false otherwise
     */
    boolean existsByUserId(String userId);

    /**
     * Checks if a candidate exists with the given email.
     *
     * @param email the email address
     * @return true if a candidate exists with the given email, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Finds candidates by location containing the given string (case insensitive) and profile visible.
     *
     * @param location the location
     * @param pageable the pagination information
     * @return the page of candidates
     */
    Page<Candidate> findByLocationContainingIgnoreCaseAndProfileVisibleTrue(String location, Pageable pageable);

    /**
     * Finds candidates who are open to work and have visible profiles.
     *
     * @param pageable the pagination information
     * @return the page of candidates
     */
    Page<Candidate> findByOpenToWorkTrueAndProfileVisibleTrue(Pageable pageable);

    /**
     * Finds candidates who are open to relocate and have visible profiles.
     *
     * @param pageable the pagination information
     * @return the page of candidates
     */
    Page<Candidate> findByOpenToRelocateTrueAndProfileVisibleTrue(Pageable pageable);

    /**
     * Searches for candidates by keyword in various fields.
     *
     * @param keyword the keyword
     * @param pageable the pagination information
     * @return the page of candidates
     */
    @Query("SELECT DISTINCT c FROM Candidate c " +
           "WHERE c.profileVisible = true " +
           "AND (LOWER(c.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(c.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(c.headline) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(c.summary) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(c.currentJobTitle) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(c.currentCompany) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(c.location) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Candidate> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    /**
     * Finds candidates with a specific skill.
     *
     * @param skillName the skill name
     * @param pageable the pagination information
     * @return the page of candidates
     */
    @Query("SELECT DISTINCT c FROM Candidate c JOIN c.skills s " +
           "WHERE c.profileVisible = true " +
           "AND LOWER(s.name) LIKE LOWER(CONCAT('%', :skillName, '%'))")
    Page<Candidate> findBySkillName(@Param("skillName") String skillName, Pageable pageable);

    /**
     * Finds candidates with experience at a specific company.
     *
     * @param company the company
     * @param pageable the pagination information
     * @return the page of candidates
     */
    @Query("SELECT DISTINCT c FROM Candidate c JOIN c.experiences e " +
           "WHERE c.profileVisible = true " +
           "AND LOWER(e.company) LIKE LOWER(CONCAT('%', :company, '%'))")
    Page<Candidate> findByCompanyExperience(@Param("company") String company, Pageable pageable);

    /**
     * Finds candidates with education at a specific institution.
     *
     * @param institution the institution
     * @param pageable the pagination information
     * @return the page of candidates
     */
    @Query("SELECT DISTINCT c FROM Candidate c JOIN c.educations e " +
           "WHERE c.profileVisible = true " +
           "AND LOWER(e.institution) LIKE LOWER(CONCAT('%', :institution, '%'))")
    Page<Candidate> findByEducationInstitution(@Param("institution") String institution, Pageable pageable);
} 