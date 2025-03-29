package com.goodjob.company.query.service;

import com.goodjob.common.dto.response.PageResponseDTO;
import com.goodjob.company.query.dto.CompanyQuery;
import com.goodjob.company.query.dto.CompanyView;

/**
 * Service interface for company query operations.
 */
public interface CompanyQueryService {

    PageResponseDTO<CompanyView> getAllCompanies(CompanyQuery query);

    CompanyView getCompanyById(Integer id);
} 