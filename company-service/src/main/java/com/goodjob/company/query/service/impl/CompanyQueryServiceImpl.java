package com.goodjob.company.query.service.impl;

import com.goodjob.common.dto.PageResponseDTO;
import com.goodjob.company.query.dto.CompanyQuery;
import com.goodjob.company.query.dto.CompanyView;
import com.goodjob.company.query.service.CompanyQueryService;
import org.springframework.stereotype.Service;

/**
 * Implementation of the CompanyQueryService interface.
 */
@Service
public class CompanyQueryServiceImpl implements CompanyQueryService {

    @Override
    public PageResponseDTO<CompanyView> getAllCompanies(CompanyQuery query) {
        return null;
    }
}