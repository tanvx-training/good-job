package com.goodjob.company.domain.company.repository;

import com.goodjob.company.domain.company.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Company entity.
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    @EntityGraph(attributePaths = {"companyMetric", "companySpecialities", "companyIndustries"})
    Page<CompanySummary> findByDeleteFlg(boolean deleteFlg, Pageable pageable);
}