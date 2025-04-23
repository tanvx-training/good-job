package com.goodjob.company.domain.company.query;

import com.goodjob.common.application.dto.response.PageResponseDTO;
import com.goodjob.company.domain.company.dto.CompanyQuery;
import com.goodjob.company.domain.company.dto.CompanyView;

/**
 * Service interface for company query operations.
 */
public interface CompanyQueryService {

    PageResponseDTO<CompanyView> getAllCompanies(CompanyQuery query);

    CompanyView getCompanyById(Integer id);
} 