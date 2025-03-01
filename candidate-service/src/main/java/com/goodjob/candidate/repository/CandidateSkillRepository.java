package com.goodjob.candidate.repository;

import com.goodjob.candidate.entity.CandidateSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for CandidateSkill entity.
 */
@Repository
public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, String> {

    /**
     * Finds all skills for a candidate.
     *
     * @param candidateId the candidate ID
     * @return the list of candidate skills
     */
    List<CandidateSkill> findByCandidateId(String candidateId);

    /**
     * Finds a skill by candidate ID and skill ID.
     *
     * @param candidateId the candidate ID
     * @param skillId the skill ID
     * @return the optional candidate skill
     */
    Optional<CandidateSkill> findByCandidateIdAndId(String candidateId, String skillId);

    /**
     * Finds a skill by candidate ID and skill name.
     *
     * @param candidateId the candidate ID
     * @param name the skill name
     * @return the optional candidate skill
     */
    Optional<CandidateSkill> findByCandidateIdAndNameIgnoreCase(String candidateId, String name);

    /**
     * Deletes all skills for a candidate.
     *
     * @param candidateId the candidate ID
     */
    void deleteByCandidateId(String candidateId);

    /**
     * Finds the most common skills across all candidates.
     *
     * @param limit the maximum number of skills to return
     * @return the list of skill names and their counts
     */
    @Query(value = "SELECT cs.name, COUNT(cs.id) as count " +
                  "FROM candidate_skill cs " +
                  "JOIN candidate c ON cs.candidate_id = c.id " +
                  "WHERE c.profile_visible = true " +
                  "GROUP BY cs.name " +
                  "ORDER BY count DESC " +
                  "LIMIT :limit", nativeQuery = true)
    List<Object[]> findMostCommonSkills(@Param("limit") int limit);

    /**
     * Finds skills matching the given name (case insensitive).
     *
     * @param name the skill name
     * @param limit the maximum number of skills to return
     * @return the list of distinct skill names
     */
    @Query(value = "SELECT DISTINCT cs.name " +
                  "FROM candidate_skill cs " +
                  "WHERE LOWER(cs.name) LIKE LOWER(CONCAT('%', :name, '%')) " +
                  "ORDER BY cs.name " +
                  "LIMIT :limit", nativeQuery = true)
    List<String> findSkillsByNameContaining(@Param("name") String name, @Param("limit") int limit);
} 