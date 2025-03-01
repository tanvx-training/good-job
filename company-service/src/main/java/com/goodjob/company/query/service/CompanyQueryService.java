package com.goodjob.company.query.service;

import com.goodjob.company.dto.CompanyResponse;
import com.goodjob.company.dto.CompanySearchCriteria;
import com.goodjob.company.exception.CompanyNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Service interface for company query operations.
 */
public interface CompanyQueryService {

    /**
     * Get a company by ID.
     *
     * @param id the ID of the company to retrieve
     * @return the company response
     * @throws CompanyNotFoundException if the company with the given ID is not found
     */
    CompanyResponse getCompanyById(Integer id) throws CompanyNotFoundException;

    /**
     * Get companies by employer ID.
     *
     * @param employerId the ID of the employer
     * @param page       the page number (0-based)
     * @param size       the page size
     * @param sort       the field to sort by
     * @param direction  the sort direction (ASC or DESC)
     * @return a page of company responses
     */
    Page<CompanyResponse> getCompaniesByEmployerId(Integer employerId, int page, int size, String sort, String direction);

    /**
     * Get verified companies.
     *
     * @param page      the page number (0-based)
     * @param size      the page size
     * @param sort      the field to sort by
     * @param direction the sort direction (ASC or DESC)
     * @return a page of verified company responses
     */
    Page<CompanyResponse> getVerifiedCompanies(int page, int size, String sort, String direction);

    /**
     * Search companies based on criteria.
     *
     * @param criteria  the search criteria
     * @param page      the page number (0-based)
     * @param size      the page size
     * @param sort      the field to sort by
     * @param direction the sort direction (ASC or DESC)
     * @return a page of company responses matching the criteria
     */
    Page<CompanyResponse> searchCompanies(CompanySearchCriteria criteria, int page, int size, String sort, String direction);

    /**
     * Get top rated companies.
     *
     * @param limit the maximum number of companies to return
     * @return a list of top rated company responses
     */
    List<CompanyResponse> getTopRatedCompanies(int limit);

    /**
     * Get recently added companies.
     *
     * @param limit the maximum number of companies to return
     * @return a list of recently added company responses
     */
    List<CompanyResponse> getRecentlyAddedCompanies(int limit);

    /**
     * Get company by name.
     *
     * @param name the name of the company
     * @return the company response
     * @throws CompanyNotFoundException if the company with the given name is not found
     */
    CompanyResponse getCompanyByName(String name) throws CompanyNotFoundException;
} 