package com.goodjob.candidate.repository;

import com.goodjob.candidate.entity.CandidateProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for CandidateProject entity.
 */
@Repository
public interface CandidateProjectRepository extends JpaRepository<CandidateProject, String> {

    /**
     * Finds all projects for a candidate.
     *
     * @param candidateId the candidate ID
     * @return the list of candidate projects
     */
    List<CandidateProject> findByCandidateId(String candidateId);

    /**
     * Finds all projects for a candidate, ordered by start date descending.
     *
     * @param candidateId the candidate ID
     * @return the list of candidate projects
     */
    List<CandidateProject> findByCandidateIdOrderByStartDateDesc(String candidateId);

    /**
     * Finds a project by candidate ID and project ID.
     *
     * @param candidateId the candidate ID
     * @param projectId the project ID
     * @return the optional candidate project
     */
    Optional<CandidateProject> findByCandidateIdAndId(String candidateId, String projectId);

    /**
     * Deletes all projects for a candidate.
     *
     * @param candidateId the candidate ID
     */
    void deleteByCandidateId(String candidateId);

    /**
     * Finds the most common technologies used in projects.
     *
     * @param limit the maximum number of technologies to return
     * @return the list of technology names and their counts
     */
    @Query(value = "SELECT UNNEST(STRING_TO_ARRAY(cp.technologies, ',')) as tech, COUNT(*) as count " +
                  "FROM candidate_project cp " +
                  "JOIN candidate c ON cp.candidate_id = c.id " +
                  "WHERE c.profile_visible = true " +
                  "GROUP BY tech " +
                  "ORDER BY count DESC " +
                  "LIMIT :limit", nativeQuery = true)
    List<Object[]> findMostCommonTechnologies(@Param("limit") int limit);

    /**
     * Finds projects with technologies matching the given name (case insensitive).
     *
     * @param technology the technology name
     * @param pageable the pagination information
     * @return the list of candidate projects
     */
    @Query(value = "SELECT cp.* " +
                  "FROM candidate_project cp " +
                  "JOIN candidate c ON cp.candidate_id = c.id " +
                  "WHERE c.profile_visible = true " +
                  "AND LOWER(cp.technologies) LIKE LOWER(CONCAT('%', :technology, '%')) " +
                  "ORDER BY cp.start_date DESC", nativeQuery = true)
    List<CandidateProject> findByTechnology(@Param("technology") String technology);
} 