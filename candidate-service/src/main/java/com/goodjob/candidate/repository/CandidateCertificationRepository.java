package com.goodjob.candidate.repository;

import com.goodjob.candidate.entity.CandidateCertification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for CandidateCertification entity.
 */
@Repository
public interface CandidateCertificationRepository extends JpaRepository<CandidateCertification, String> {

    /**
     * Finds all certifications for a candidate.
     *
     * @param candidateId the candidate ID
     * @return the list of candidate certifications
     */
    List<CandidateCertification> findByCandidateId(String candidateId);

    /**
     * Finds all certifications for a candidate, ordered by issue date descending.
     *
     * @param candidateId the candidate ID
     * @return the list of candidate certifications
     */
    List<CandidateCertification> findByCandidateIdOrderByIssueDateDesc(String candidateId);

    /**
     * Finds a certification by candidate ID and certification ID.
     *
     * @param candidateId the candidate ID
     * @param certificationId the certification ID
     * @return the optional candidate certification
     */
    Optional<CandidateCertification> findByCandidateIdAndId(String candidateId, String certificationId);

    /**
     * Deletes all certifications for a candidate.
     *
     * @param candidateId the candidate ID
     */
    void deleteByCandidateId(String candidateId);

    /**
     * Finds the most common certifications.
     *
     * @param limit the maximum number of certifications to return
     * @return the list of certification names and their counts
     */
    @Query(value = "SELECT cc.name, COUNT(cc.id) as count " +
                  "FROM candidate_certification cc " +
                  "JOIN candidate c ON cc.candidate_id = c.id " +
                  "WHERE c.profile_visible = true " +
                  "GROUP BY cc.name " +
                  "ORDER BY count DESC " +
                  "LIMIT :limit", nativeQuery = true)
    List<Object[]> findMostCommonCertifications(@Param("limit") int limit);

    /**
     * Finds the most common certification issuing organizations.
     *
     * @param limit the maximum number of organizations to return
     * @return the list of organization names and their counts
     */
    @Query(value = "SELECT cc.issuing_organization, COUNT(cc.id) as count " +
                  "FROM candidate_certification cc " +
                  "JOIN candidate c ON cc.candidate_id = c.id " +
                  "WHERE c.profile_visible = true " +
                  "GROUP BY cc.issuing_organization " +
                  "ORDER BY count DESC " +
                  "LIMIT :limit", nativeQuery = true)
    List<Object[]> findMostCommonIssuingOrganizations(@Param("limit") int limit);

    /**
     * Finds certifications matching the given name (case insensitive).
     *
     * @param name the certification name
     * @param limit the maximum number of certifications to return
     * @return the list of distinct certification names
     */
    @Query(value = "SELECT DISTINCT cc.name " +
                  "FROM candidate_certification cc " +
                  "WHERE LOWER(cc.name) LIKE LOWER(CONCAT('%', :name, '%')) " +
                  "ORDER BY cc.name " +
                  "LIMIT :limit", nativeQuery = true)
    List<String> findCertificationsByNameContaining(@Param("name") String name, @Param("limit") int limit);

    /**
     * Finds certifications by issuing organization (case insensitive).
     *
     * @param organization the issuing organization name
     * @param limit the maximum number of certifications to return
     * @return the list of candidate certifications
     */
    @Query(value = "SELECT cc.* " +
                  "FROM candidate_certification cc " +
                  "JOIN candidate c ON cc.candidate_id = c.id " +
                  "WHERE c.profile_visible = true " +
                  "AND LOWER(cc.issuing_organization) LIKE LOWER(CONCAT('%', :organization, '%')) " +
                  "ORDER BY cc.issue_date DESC " +
                  "LIMIT :limit", nativeQuery = true)
    List<CandidateCertification> findByIssuingOrganization(@Param("organization") String organization, @Param("limit") int limit);
} 