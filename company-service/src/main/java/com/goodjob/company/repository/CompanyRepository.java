package com.goodjob.company.repository;

import com.goodjob.company.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Company entity.
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer>, JpaSpecificationExecutor<Company> {

    /**
     * Find a company by ID and employer ID.
     *
     * @param id         the company ID
     * @param employerId the employer ID
     * @return an optional company
     */
    Optional<Company> findByIdAndEmployerId(Integer id, Integer employerId);

    /**
     * Find a company by name.
     *
     * @param name the company name
     * @return an optional company
     */
    Optional<Company> findByNameIgnoreCase(String name);

    /**
     * Find companies by employer ID.
     *
     * @param employerId the employer ID
     * @param pageable   the pagination information
     * @return a page of companies
     */
    Page<Company> findByEmployerId(Integer employerId, Pageable pageable);

    /**
     * Find companies by industry.
     *
     * @param industry the industry
     * @param pageable the pagination information
     * @return a page of companies
     */
    Page<Company> findByIndustryIgnoreCase(String industry, Pageable pageable);

    /**
     * Find companies by headquarters location.
     *
     * @param headquarters the headquarters location
     * @param pageable     the pagination information
     * @return a page of companies
     */
    Page<Company> findByHeadquartersContainingIgnoreCase(String headquarters, Pageable pageable);

    /**
     * Find verified companies.
     *
     * @param pageable the pagination information
     * @return a page of verified companies
     */
    Page<Company> findByVerifiedTrue(Pageable pageable);

    /**
     * Find recently added verified companies.
     *
     * @param pageable the pagination information
     * @return a list of recently added verified companies
     */
    List<Company> findByVerifiedTrueOrderByCreatedAtDesc(Pageable pageable);

    /**
     * Search for companies using PostgreSQL full-text search.
     *
     * @param query    the search query
     * @param pageable the pagination information
     * @return a page of companies
     */
    @Query(value = "SELECT c.* FROM companies c WHERE c.search_vector @@ plainto_tsquery('english', :query) " +
            "ORDER BY ts_rank(c.search_vector, plainto_tsquery('english', :query)) DESC",
            countQuery = "SELECT COUNT(*) FROM companies c WHERE c.search_vector @@ plainto_tsquery('english', :query)",
            nativeQuery = true)
    Page<Company> searchCompanies(@Param("query") String query, Pageable pageable);

    /**
     * Update the search vector of a company.
     *
     * @param id           the company ID
     * @param searchVector the search vector
     * @return the number of rows affected
     */
    @Modifying
    @Query("UPDATE Company c SET c.searchVector = :searchVector WHERE c.id = :id")
    int updateSearchVector(@Param("id") Integer id, @Param("searchVector") String searchVector);
}