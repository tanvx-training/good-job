package com.goodjob.company.query.service.impl;

import com.goodjob.common.dto.PageResponseDTO;
import com.goodjob.common.util.DateTimeUtils;
import com.goodjob.company.common.enums.CompanySize;
import com.goodjob.company.query.dto.*;
import com.goodjob.company.query.service.CompanyQueryService;
import com.goodjob.company.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of the CompanyQueryService interface.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyQueryServiceImpl implements CompanyQueryService {

    private final CompanyRepository companyRepository;

    @Override
    public PageResponseDTO<CompanyView> getAllCompanies(CompanyQuery query) {

        String[] parts = query.getSort().split(",");
        Sort sort = Sort.by(Sort.Direction.fromString(parts[1]), parts[0]);
        Pageable pageable = PageRequest.of(query.getPage(), query.getSize(), sort);

        Page<CompanyView> companyViewPage = companyRepository.findByDeleteFlg(false, pageable)
                .map(summary -> {
                    log.info("Summary={}", summary);
                    return this.convertToCompanyView(summary);
                });
        return new PageResponseDTO<>(companyViewPage);
    }

    private CompanyView convertToCompanyView(CompanySummary summary) {
        CompanyMetricSummary cms = summary.getCompanyMetric();
        List<CompanyIndustrySummary> cisList = summary.getCompanyIndustries();
        List<CompanySpecialitySummary> cssList = summary.getCompanySpecialities();
        return CompanyView.builder()
                .companyId(summary.getCompanyId())
                .name(summary.getName())
                .description(summary.getDescription())
                .companySize(CompanySize.fromValue(summary.getCompanySize()).getDescription())
                .address(buildAddress(summary))
                .url(summary.getUrl())
                .metric(CompanyMetricView.builder()
                        .employeeCount(cms.getEmployeeCount())
                        .followerCount(cms.getFollowerCount())
                        .recordOn(Objects.nonNull(cms.getRecordOn())
                                ? DateTimeUtils.fromTimestamp(cms.getRecordOn())
                                : null)
                        .build())
                .industries(cisList
                        .stream()
                        .map(cis -> CompanyIndustryView.builder()
                                .industryName(String.valueOf(cis.getIndustryId()))
                                .build())
                        .toList())
                .specialities(cssList
                        .stream()
                        .map(css -> CompanySpecialityView.builder()
                                .name(String.valueOf(css.getSpecialityId()))
                                .build())
                        .toList())
                .build();
    }

    private String buildAddress (CompanySummary summary) {

        // Use StringBuilder for efficient string construction
        StringBuilder sb = new StringBuilder();

        // Add street address if present
        if (StringUtils.hasText(summary.getAddress())) {
            sb.append(summary.getAddress());
        }

        // Add city if present, with a comma if there's prior content
        if (StringUtils.hasText(summary.getCity())) {
            if (!sb.isEmpty()) {
                sb.append(", ");
            }
            sb.append(summary.getCity());
        }

        // Combine state and zip code as a unit (e.g., "IL 62704")
        String stateZip = "";
        if (StringUtils.hasText(summary.getState())) {
            stateZip += summary.getState();
        }
        if (StringUtils.hasText(summary.getZipCode())) {
            if (!stateZip.isEmpty()) {
                stateZip += " ";// Space between state and zip
            }
            stateZip += summary.getZipCode();
        }
        if (!stateZip.isEmpty()) {
            if (!sb.isEmpty()) {
                sb.append(", ");
            }
            sb.append(stateZip);
        }

        // Add country if present
        if (StringUtils.hasText(summary.getCountry())) {
            if (!sb.isEmpty()) {
                sb.append(", ");
            }
            sb.append(summary.getCountry());
        }

        return sb.toString();
    }
}