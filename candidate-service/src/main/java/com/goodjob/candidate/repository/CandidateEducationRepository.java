package com.goodjob.candidate.repository;

import com.goodjob.candidate.entity.CandidateEducation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for CandidateEducation entity.
 */
@Repository
public interface CandidateEducationRepository extends JpaRepository<CandidateEducation, String> {

    /**
     * Finds all educations for a candidate.
     *
     * @param candidateId the candidate ID
     * @return the list of candidate educations
     */
    List<CandidateEducation> findByCandidateId(String candidateId);

    /**
     * Finds all educations for a candidate, ordered by start date descending.
     *
     * @param candidateId the candidate ID
     * @return the list of candidate educations
     */
    List<CandidateEducation> findByCandidateIdOrderByStartDateDesc(String candidateId);

    /**
     * Finds an education by candidate ID and education ID.
     *
     * @param candidateId the candidate ID
     * @param educationId the education ID
     * @return the optional candidate education
     */
    Optional<CandidateEducation> findByCandidateIdAndId(String candidateId, String educationId);

    /**
     * Deletes all educations for a candidate.
     *
     * @param candidateId the candidate ID
     */
    void deleteByCandidateId(String candidateId);

    /**
     * Finds the most common educational institutions.
     *
     * @param limit the maximum number of institutions to return
     * @return the list of institution names and their counts
     */
    @Query(value = "SELECT ce.institution, COUNT(ce.id) as count " +
                  "FROM candidate_education ce " +
                  "JOIN candidate c ON ce.candidate_id = c.id " +
                  "WHERE c.profile_visible = true " +
                  "GROUP BY ce.institution " +
                  "ORDER BY count DESC " +
                  "LIMIT :limit", nativeQuery = true)
    List<Object[]> findMostCommonInstitutions(@Param("limit") int limit);

    /**
     * Finds the most common degrees.
     *
     * @param limit the maximum number of degrees to return
     * @return the list of degree names and their counts
     */
    @Query(value = "SELECT ce.degree, COUNT(ce.id) as count " +
                  "FROM candidate_education ce " +
                  "JOIN candidate c ON ce.candidate_id = c.id " +
                  "WHERE c.profile_visible = true " +
                  "GROUP BY ce.degree " +
                  "ORDER BY count DESC " +
                  "LIMIT :limit", nativeQuery = true)
    List<Object[]> findMostCommonDegrees(@Param("limit") int limit);

    /**
     * Finds the most common fields of study.
     *
     * @param limit the maximum number of fields of study to return
     * @return the list of field of study names and their counts
     */
    @Query(value = "SELECT ce.field_of_study, COUNT(ce.id) as count " +
                  "FROM candidate_education ce " +
                  "JOIN candidate c ON ce.candidate_id = c.id " +
                  "WHERE c.profile_visible = true " +
                  "GROUP BY ce.field_of_study " +
                  "ORDER BY count DESC " +
                  "LIMIT :limit", nativeQuery = true)
    List<Object[]> findMostCommonFieldsOfStudy(@Param("limit") int limit);

    /**
     * Finds institutions matching the given name (case insensitive).
     *
     * @param name the institution name
     * @param limit the maximum number of institutions to return
     * @return the list of distinct institution names
     */
    @Query(value = "SELECT DISTINCT ce.institution " +
                  "FROM candidate_education ce " +
                  "WHERE LOWER(ce.institution) LIKE LOWER(CONCAT('%', :name, '%')) " +
                  "ORDER BY ce.institution " +
                  "LIMIT :limit", nativeQuery = true)
    List<String> findInstitutionsByNameContaining(@Param("name") String name, @Param("limit") int limit);
} 