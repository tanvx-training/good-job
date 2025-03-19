package com.goodjob.company.command.service;

import com.goodjob.company.common.dto.CompanyCommand;
import com.goodjob.company.exception.CompanyAlreadyExistsException;
import com.goodjob.company.exception.CompanyNotFoundException;

/**
 * Service interface for company command operations.
 */
public interface CompanyCommandService {

    /**
     * Create a new company.
     *
     * @param companyCommand the company command containing company details
     * @param employerId     the ID of the employer creating the company
     * @return the ID of the created company
     * @throws CompanyAlreadyExistsException if a company with the same name already exists
     */
    Integer createCompany(CompanyCommand companyCommand, Integer employerId) throws CompanyAlreadyExistsException;

    /**
     * Update an existing company.
     *
     * @param id             the ID of the company to update
     * @param companyCommand the company command containing updated company details
     * @param employerId     the ID of the employer updating the company
     * @throws CompanyNotFoundException    if the company with the given ID is not found
     * @throws UnauthorizedAccessException if the employer is not authorized to update the company
     */
    void updateCompany(Integer id, CompanyCommand companyCommand, Integer employerId) 
            throws CompanyNotFoundException, UnauthorizedAccessException;

    /**
     * Delete a company.
     *
     * @param id         the ID of the company to delete
     * @param employerId the ID of the employer deleting the company
     * @throws CompanyNotFoundException    if the company with the given ID is not found
     * @throws UnauthorizedAccessException if the employer is not authorized to delete the company
     */
    void deleteCompany(Integer id, Integer employerId) 
            throws CompanyNotFoundException, UnauthorizedAccessException;

    /**
     * Verify a company.
     *
     * @param id the ID of the company to verify
     * @throws CompanyNotFoundException if the company with the given ID is not found
     */
    void verifyCompany(Integer id) throws CompanyNotFoundException;

    /**
     * Update company logo.
     *
     * @param id         the ID of the company
     * @param logoUrl    the URL of the company logo
     * @param employerId the ID of the employer updating the logo
     * @throws CompanyNotFoundException    if the company with the given ID is not found
     * @throws UnauthorizedAccessException if the employer is not authorized to update the company
     */
    void updateCompanyLogo(Integer id, String logoUrl, Integer employerId) 
            throws CompanyNotFoundException, UnauthorizedAccessException;

    /**
     * Update company banner.
     *
     * @param id         the ID of the company
     * @param bannerUrl  the URL of the company banner
     * @param employerId the ID of the employer updating the banner
     * @throws CompanyNotFoundException    if the company with the given ID is not found
     * @throws UnauthorizedAccessException if the employer is not authorized to update the company
     */
    void updateCompanyBanner(Integer id, String bannerUrl, Integer employerId) 
            throws CompanyNotFoundException, UnauthorizedAccessException;
} 