package com.goodjob.company.query.service.impl;

import com.goodjob.company.dto.CompanyResponse;
import com.goodjob.company.dto.CompanySearchCriteria;
import com.goodjob.company.entity.Company;
import com.goodjob.company.entity.CompanyMetrics;
import com.goodjob.company.exception.CompanyNotFoundException;
import com.goodjob.company.query.service.CompanyQueryService;
import com.goodjob.company.repository.CompanyMetricsRepository;
import com.goodjob.company.repository.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the CompanyQueryService interface.
 */
@Service
public class CompanyQueryServiceImpl implements CompanyQueryService {

    private static final Logger log = LoggerFactory.getLogger(CompanyQueryServiceImpl.class);

    private final CompanyRepository companyRepository;
    private final CompanyMetricsRepository metricsRepository;

    public CompanyQueryServiceImpl(CompanyRepository companyRepository, CompanyMetricsRepository metricsRepository) {
        this.companyRepository = companyRepository;
        this.metricsRepository = metricsRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyResponse getCompanyById(Integer id) throws CompanyNotFoundException {
        log.info("Retrieving company with ID: {}", id);

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Company not found with ID: {}", id);
                    return new CompanyNotFoundException(id);
                });

        // Increment view count
        CompanyMetrics metrics = company.getMetrics();
        metrics.setViewCount(metrics.getViewCount() + 1);
        metricsRepository.save(metrics);

        return mapToCompanyResponse(company);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyResponse> getCompaniesByEmployerId(Integer employerId, int page, int size, String sort, String direction) {
        log.info("Retrieving companies for employer with ID: {}", employerId);

        Pageable pageable = createPageable(page, size, sort, direction);
        Page<Company> companies = companyRepository.findByEmployerId(employerId, pageable);

        return companies.map(this::mapToCompanyResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyResponse> getVerifiedCompanies(int page, int size, String sort, String direction) {
        log.info("Retrieving verified companies");

        Pageable pageable = createPageable(page, size, sort, direction);
        Page<Company> companies = companyRepository.findByVerifiedTrue(pageable);

        return companies.map(this::mapToCompanyResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyResponse> searchCompanies(CompanySearchCriteria criteria, int page, int size, String sort, String direction) {
        log.info("Searching companies with criteria: {}", criteria);

        Pageable pageable = createPageable(page, size, sort, direction);
        
        Specification<Company> spec = Specification.where(null);
        
        // Add search criteria
        if (StringUtils.hasText(criteria.getKeyword())) {
            String keyword = "%" + criteria.getKeyword().toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> 
                cb.or(
                    cb.like(cb.lower(root.get("name")), keyword),
                    cb.like(cb.lower(root.get("description")), keyword)
                )
            );
        }
        
        if (StringUtils.hasText(criteria.getLocation())) {
            String location = "%" + criteria.getLocation().toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> 
                cb.like(cb.lower(root.get("headquarters")), location)
            );
        }
        
        if (StringUtils.hasText(criteria.getIndustry())) {
            spec = spec.and((root, query, cb) -> 
                cb.equal(root.get("industry"), criteria.getIndustry())
            );
        }
        
        if (criteria.getCompanySize() != null) {
            spec = spec.and((root, query, cb) -> 
                cb.equal(root.get("companySize"), criteria.getCompanySize())
            );
        }
        
        if (criteria.getSpecialties() != null && !criteria.getSpecialties().isEmpty()) {
            spec = spec.and((root, query, cb) -> 
                root.join("specialties").in(criteria.getSpecialties())
            );
        }
        
        if (criteria.getVerified() != null) {
            spec = spec.and((root, query, cb) -> 
                cb.equal(root.get("verified"), criteria.getVerified())
            );
        }
        
        Page<Company> companies = companyRepository.findAll(spec, pageable);
        
        return companies.map(this::mapToCompanyResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyResponse> getTopRatedCompanies(int limit) {
        log.info("Retrieving top {} rated companies", limit);

        List<CompanyMetrics> topMetrics = metricsRepository.findTopByRating(PageRequest.of(0, limit));
        
        return topMetrics.stream()
                .map(CompanyMetrics::getCompany)
                .map(this::mapToCompanyResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyResponse> getRecentlyAddedCompanies(int limit) {
        log.info("Retrieving {} recently added companies", limit);

        List<Company> recentCompanies = companyRepository.findByVerifiedTrueOrderByCreatedAtDesc(PageRequest.of(0, limit));
        
        return recentCompanies.stream()
                .map(this::mapToCompanyResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyResponse getCompanyByName(String name) throws CompanyNotFoundException {
        log.info("Retrieving company with name: {}", name);

        Company company = companyRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> {
                    log.error("Company not found with name: {}", name);
                    return new CompanyNotFoundException("Company not found with name: " + name);
                });

        return mapToCompanyResponse(company);
    }

    /**
     * Create a Pageable object for pagination and sorting.
     *
     * @param page      the page number (0-based)
     * @param size      the page size
     * @param sort      the field to sort by
     * @param direction the sort direction (ASC or DESC)
     * @return the Pageable object
     */
    private Pageable createPageable(int page, int size, String sort, String direction) {
        Sort.Direction sortDirection = Sort.Direction.ASC;
        if (direction != null && direction.equalsIgnoreCase("desc")) {
            sortDirection = Sort.Direction.DESC;
        }

        String sortBy = "id";
        if (StringUtils.hasText(sort)) {
            sortBy = sort;
        }

        return PageRequest.of(page, size, sortDirection, sortBy);
    }

    /**
     * Map a Company entity to a CompanyResponse DTO.
     *
     * @param company the Company entity
     * @return the CompanyResponse DTO
     */
    private CompanyResponse mapToCompanyResponse(Company company) {
        CompanyMetrics metrics = company.getMetrics();
        
        CompanyResponse response = new CompanyResponse();
        response.setId(company.getId());
        response.setName(company.getName());
        response.setDescription(company.getDescription());
        response.setWebsite(company.getWebsite());
        response.setIndustry(company.getIndustry());
        response.setCompanySize(company.getCompanySize());
        response.setFoundedYear(company.getFoundedYear());
        response.setHeadquarters(company.getHeadquarters());
        response.setLogoUrl(company.getLogoUrl());
        response.setBannerUrl(company.getBannerUrl());
        response.setSpecialties(company.getSpecialties());
        response.setEmployerId(company.getEmployerId());
        response.setVerified(company.isVerified());
        response.setCreatedAt(company.getCreatedAt());
        response.setUpdatedAt(company.getUpdatedAt());
        
        // Add metrics
        if (metrics != null) {
            response.setViewCount(metrics.getViewCount());
            response.setJobCount(metrics.getJobCount());
            response.setFollowerCount(metrics.getFollowerCount());
            response.setAverageRating(metrics.getAverageRating());
        }
        
        return response;
    }
} 