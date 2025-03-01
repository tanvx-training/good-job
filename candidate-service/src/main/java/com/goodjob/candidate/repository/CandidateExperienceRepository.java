package com.goodjob.candidate.repository;

import com.goodjob.candidate.entity.CandidateExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for CandidateExperience entity.
 */
@Repository
public interface CandidateExperienceRepository extends JpaRepository<CandidateExperience, String> {

    /**
     * Finds all experiences for a candidate.
     *
     * @param candidateId the candidate ID
     * @return the list of candidate experiences
     */
    List<CandidateExperience> findByCandidateId(String candidateId);

    /**
     * Finds all experiences for a candidate, ordered by start date descending.
     *
     * @param candidateId the candidate ID
     * @return the list of candidate experiences
     */
    List<CandidateExperience> findByCandidateIdOrderByStartDateDesc(String candidateId);

    /**
     * Finds an experience by candidate ID and experience ID.
     *
     * @param candidateId the candidate ID
     * @param experienceId the experience ID
     * @return the optional candidate experience
     */
    Optional<CandidateExperience> findByCandidateIdAndId(String candidateId, String experienceId);

    /**
     * Deletes all experiences for a candidate.
     *
     * @param candidateId the candidate ID
     */
    void deleteByCandidateId(String candidateId);

    /**
     * Finds the most common companies where candidates have worked.
     *
     * @param limit the maximum number of companies to return
     * @return the list of company names and their counts
     */
    @Query(value = "SELECT ce.company, COUNT(ce.id) as count " +
                  "FROM candidate_experience ce " +
                  "JOIN candidate c ON ce.candidate_id = c.id " +
                  "WHERE c.profile_visible = true " +
                  "GROUP BY ce.company " +
                  "ORDER BY count DESC " +
                  "LIMIT :limit", nativeQuery = true)
    List<Object[]> findMostCommonCompanies(@Param("limit") int limit);

    /**
     * Finds the most common job titles.
     *
     * @param limit the maximum number of job titles to return
     * @return the list of job titles and their counts
     */
    @Query(value = "SELECT ce.title, COUNT(ce.id) as count " +
                  "FROM candidate_experience ce " +
                  "JOIN candidate c ON ce.candidate_id = c.id " +
                  "WHERE c.profile_visible = true " +
                  "GROUP BY ce.title " +
                  "ORDER BY count DESC " +
                  "LIMIT :limit", nativeQuery = true)
    List<Object[]> findMostCommonJobTitles(@Param("limit") int limit);

    /**
     * Finds companies matching the given name (case insensitive).
     *
     * @param name the company name
     * @param limit the maximum number of companies to return
     * @return the list of distinct company names
     */
    @Query(value = "SELECT DISTINCT ce.company " +
                  "FROM candidate_experience ce " +
                  "WHERE LOWER(ce.company) LIKE LOWER(CONCAT('%', :name, '%')) " +
                  "ORDER BY ce.company " +
                  "LIMIT :limit", nativeQuery = true)
    List<String> findCompaniesByNameContaining(@Param("name") String name, @Param("limit") int limit);
} 