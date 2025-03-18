package com.goodjob.company.command.service.impl;

import com.goodjob.company.command.service.CompanyCommandService;
import com.goodjob.company.dto.CompanyCommand;
import com.goodjob.company.entity.Company;
import com.goodjob.company.entity.CompanyMetric;
import com.goodjob.company.exception.CompanyAlreadyExistsException;
import com.goodjob.company.exception.CompanyNotFoundException;
import com.goodjob.company.exception.UnauthorizedAccessException;
import com.goodjob.company.repository.CompanyMetricsRepository;
import com.goodjob.company.repository.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;

/**
 * Implementation of the CompanyCommandService interface.
 */
@Service
public class CompanyCommandServiceImpl implements CompanyCommandService {

    private static final Logger log = LoggerFactory.getLogger(CompanyCommandServiceImpl.class);

    private final CompanyRepository companyRepository;
    private final CompanyMetricsRepository metricsRepository;

    public CompanyCommandServiceImpl(CompanyRepository companyRepository, CompanyMetricsRepository metricsRepository) {
        this.companyRepository = companyRepository;
        this.metricsRepository = metricsRepository;
    }

    @Override
    @Transactional
    public Integer createCompany(CompanyCommand companyCommand, Integer employerId) throws CompanyAlreadyExistsException {
        log.info("Creating company with name: {}", companyCommand.getName());

        // Check if company with the same name already exists
        if (companyRepository.findByNameIgnoreCase(companyCommand.getName()).isPresent()) {
            log.error("Company with name {} already exists", companyCommand.getName());
            throw new CompanyAlreadyExistsException(companyCommand.getName());
        }

        // Create new company
        Company company = new Company();
        company.setName(companyCommand.getName());
        company.setDescription(companyCommand.getDescription());
        company.setWebsite(companyCommand.getWebsite());
        company.setIndustry(companyCommand.getIndustry());
        company.setFoundedYear(companyCommand.getFoundedYear());
        company.setCompanySize(companyCommand.getCompanySize());
        company.setHeadquarters(companyCommand.getHeadquarters());
        company.setSpecialties(new HashSet<>(companyCommand.getSpecialties()));
        company.setLogoUrl(companyCommand.getLogoUrl());
        company.setBannerUrl(companyCommand.getBannerUrl());
        company.setEmployerId(employerId);
        company.setVerified(false);
        company.setCreatedAt(LocalDateTime.now());
        company.setUpdatedAt(LocalDateTime.now());

        Company savedCompany = companyRepository.save(company);
        
        // Create company metrics
        CompanyMetric metrics = new CompanyMetric();
        metrics.setCompany(savedCompany);
        metrics.setViewCount(0);
        metrics.setFollowerCount(0);
        metrics.setJobCount(0);
        metrics.setRatingSum(0);
        metrics.setRatingCount(0);
        metricsRepository.save(metrics);

        log.info("Company created successfully with ID: {}", savedCompany.getId());
        return savedCompany.getId();
    }

    @Override
    @Transactional
    public void updateCompany(Integer id, CompanyCommand companyCommand, Integer employerId) 
            throws CompanyNotFoundException, UnauthorizedAccessException {
        log.info("Updating company with ID: {}", id);

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Company not found with ID: {}", id);
                    return new CompanyNotFoundException(id);
                });

        // Check if the employer is authorized to update the company
        if (!company.getEmployerId().equals(employerId)) {
            log.error("Employer with ID {} is not authorized to update company with ID {}", employerId, id);
            throw new UnauthorizedAccessException("You are not authorized to update this company");
        }

        // Check if company name is being changed and if the new name already exists
        if (!company.getName().equalsIgnoreCase(companyCommand.getName()) &&
                companyRepository.findByNameIgnoreCase(companyCommand.getName()).isPresent()) {
            log.error("Company with name {} already exists", companyCommand.getName());
            throw new CompanyAlreadyExistsException(companyCommand.getName());
        }

        // Update company details
        company.setName(companyCommand.getName());
        company.setDescription(companyCommand.getDescription());
        company.setWebsite(companyCommand.getWebsite());
        company.setIndustry(companyCommand.getIndustry());
        company.setFoundedYear(companyCommand.getFoundedYear());
        company.setCompanySize(companyCommand.getCompanySize());
        company.setHeadquarters(companyCommand.getHeadquarters());
        company.setSpecialties(new HashSet<>(companyCommand.getSpecialties()));
        company.setUpdatedAt(LocalDateTime.now());

        companyRepository.save(company);
        log.info("Company updated successfully with ID: {}", id);
    }

    @Override
    @Transactional
    public void deleteCompany(Integer id, Integer employerId) 
            throws CompanyNotFoundException, UnauthorizedAccessException {
        log.info("Deleting company with ID: {}", id);

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Company not found with ID: {}", id);
                    return new CompanyNotFoundException(id);
                });

        // Check if the employer is authorized to delete the company
        if (!company.getEmployerId().equals(employerId)) {
            log.error("Employer with ID {} is not authorized to delete company with ID {}", employerId, id);
            throw new UnauthorizedAccessException("You are not authorized to delete this company");
        }

        companyRepository.delete(company);
        log.info("Company deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional
    public void verifyCompany(Integer id) throws CompanyNotFoundException {
        log.info("Verifying company with ID: {}", id);

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Company not found with ID: {}", id);
                    return new CompanyNotFoundException(id);
                });

        company.setVerified(true);
        company.setUpdatedAt(LocalDateTime.now());

        companyRepository.save(company);
        log.info("Company verified successfully with ID: {}", id);
    }

    @Override
    @Transactional
    public void updateCompanyLogo(Integer id, String logoUrl, Integer employerId) 
            throws CompanyNotFoundException, UnauthorizedAccessException {
        log.info("Updating logo for company with ID: {}", id);

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Company not found with ID: {}", id);
                    return new CompanyNotFoundException(id);
                });

        // Check if the employer is authorized to update the company
        if (!company.getEmployerId().equals(employerId)) {
            log.error("Employer with ID {} is not authorized to update company with ID {}", employerId, id);
            throw new UnauthorizedAccessException("You are not authorized to update this company");
        }

        company.setLogoUrl(logoUrl);
        company.setUpdatedAt(LocalDateTime.now());

        companyRepository.save(company);
        log.info("Company logo updated successfully with ID: {}", id);
    }

    @Override
    @Transactional
    public void updateCompanyBanner(Integer id, String bannerUrl, Integer employerId) 
            throws CompanyNotFoundException, UnauthorizedAccessException {
        log.info("Updating banner for company with ID: {}", id);

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Company not found with ID: {}", id);
                    return new CompanyNotFoundException(id);
                });

        // Check if the employer is authorized to update the company
        if (!company.getEmployerId().equals(employerId)) {
            log.error("Employer with ID {} is not authorized to update company with ID {}", employerId, id);
            throw new UnauthorizedAccessException("You are not authorized to update this company");
        }

        company.setBannerUrl(bannerUrl);
        company.setUpdatedAt(LocalDateTime.now());

        companyRepository.save(company);
        log.info("Company banner updated successfully with ID: {}", id);
    }
} 