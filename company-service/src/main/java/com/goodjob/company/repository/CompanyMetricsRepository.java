package com.goodjob.company.repository;

import com.goodjob.company.entity.CompanyMetric;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for CompanyMetrics entity.
 */
@Repository
public interface CompanyMetricsRepository extends JpaRepository<CompanyMetric, Integer> {

    /**
     * Find metrics by company ID.
     *
     * @param companyId the company ID
     * @return an optional company metrics
     */
    Optional<CompanyMetric> findByCompanyId(Integer companyId);

    /**
     * Find top companies by rating.
     *
     * @param pageable the pagination information
     * @return a list of company metrics
     */
    @Query("SELECT cm FROM CompanyMetric cm WHERE cm.ratingCount > 0 ORDER BY cm.ratingSum / cm.ratingCount DESC")
    List<CompanyMetric> findTopByRating(Pageable pageable);

    /**
     * Increment the view count of a company.
     *
     * @param companyId the company ID
     * @return the number of rows affected
     */
    @Modifying
    @Query("UPDATE CompanyMetric cm SET cm.viewCount = cm.viewCount + 1 WHERE cm.company.id = :companyId")
    int incrementViewCount(@Param("companyId") Integer companyId);

    /**
     * Increment the job count of a company.
     *
     * @param companyId the company ID
     * @return the number of rows affected
     */
    @Modifying
    @Query("UPDATE CompanyMetric cm SET cm.jobCount = cm.jobCount + 1 WHERE cm.company.id = :companyId")
    int incrementJobCount(@Param("companyId") Integer companyId);

    /**
     * Decrement the job count of a company.
     *
     * @param companyId the company ID
     * @return the number of rows affected
     */
    @Modifying
    @Query("UPDATE CompanyMetric cm SET cm.jobCount = GREATEST(0, cm.jobCount - 1) WHERE cm.company.id = :companyId")
    int decrementJobCount(@Param("companyId") Integer companyId);

    /**
     * Increment the follower count of a company.
     *
     * @param companyId the company ID
     * @return the number of rows affected
     */
    @Modifying
    @Query("UPDATE CompanyMetric cm SET cm.followerCount = cm.followerCount + 1 WHERE cm.company.id = :companyId")
    int incrementFollowerCount(@Param("companyId") Integer companyId);

    /**
     * Decrement the follower count of a company.
     *
     * @param companyId the company ID
     * @return the number of rows affected
     */
    @Modifying
    @Query("UPDATE CompanyMetric cm SET cm.followerCount = GREATEST(0, cm.followerCount - 1) WHERE cm.company.id = :companyId")
    int decrementFollowerCount(@Param("companyId") Integer companyId);

    /**
     * Add a rating to a company.
     *
     * @param companyId the company ID
     * @param rating    the rating value
     * @return the number of rows affected
     */
    @Modifying
    @Query("UPDATE CompanyMetric cm SET cm.ratingSum = cm.ratingSum + :rating, cm.ratingCount = cm.ratingCount + 1 WHERE cm.company.id = :companyId")
    int addRating(@Param("companyId") Integer companyId, @Param("rating") Integer rating);
} 