package com.goodjob.company.domain.company.query.impl;

import com.goodjob.common.application.dto.response.PageResponseDTO;
import com.goodjob.common.application.exception.ResourceNotFoundException;
import com.goodjob.common.infrastructure.util.DateTimeUtils;
import com.goodjob.company.domain.company.dto.*;
import com.goodjob.company.infrastructure.common.dto.AddressDto;
import com.goodjob.company.infrastructure.common.enums.CompanySize;
import com.goodjob.company.domain.company.entity.Company;
import com.goodjob.company.domain.company.entity.CompanyIndustry;
import com.goodjob.company.domain.company.entity.CompanyMetric;
import com.goodjob.company.domain.company.entity.CompanySpeciality;
import com.goodjob.company.domain.company.entity.id.CompanyIndustryId;
import com.goodjob.company.domain.company.entity.id.CompanySpecialityId;
import com.goodjob.company.domain.company.query.CompanyQueryService;
import com.goodjob.company.domain.company.repository.CompanyIndustrySummary;
import com.goodjob.company.domain.company.repository.CompanyMetricSummary;
import com.goodjob.company.domain.company.repository.CompanyRepository;
import com.goodjob.company.domain.company.repository.CompanySpecialitySummary;
import com.goodjob.company.domain.company.repository.CompanySummary;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.goodjob.company.infrastructure.helper.CompanyHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Implementation of the CompanyQueryService interface.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyQueryServiceImpl implements CompanyQueryService {

    private final CompanyRepository companyRepository;

    private final CompanyHelper companyHelper;

    @Override
    public PageResponseDTO<CompanyView> getAllCompanies(CompanyQuery query) {

        String[] parts = query.getSort().split(",");
        Sort sort = Sort.by(Sort.Direction.fromString(parts[1]), parts[0]);
        Pageable pageable = PageRequest.of(query.getPage(), query.getSize(), sort);

        Page<CompanyView> companyViewPage = companyRepository.findByDeleteFlg(false, pageable)
                .map(summary -> {
                    log.info("Summary={}", summary);
                    try {
                        return this.convertFromSummaryToView(summary);
                    } catch (ExecutionException | InterruptedException e) {
                        Thread.currentThread().interrupt(); // Preserve interrupt status
                        throw new RuntimeException("Error converting summary to view", e);
                    }
                });

        return new PageResponseDTO<>(companyViewPage);
    }

    @Override
    public CompanyView getCompanyById(Integer id) {
        return companyRepository.findById(id)
                .map(company -> {
                    try {
                        return this.convertFromEntityToView(company);
                    } catch (ExecutionException | InterruptedException e) {
                        Thread.currentThread().interrupt(); // Preserve interrupt status
                        throw new RuntimeException("Error converting summary to view", e);
                    }
                })
                .orElseThrow(() -> new ResourceNotFoundException(Company.class.getName(), "ID", id));
    }

    @Override
    public List<CompanyView> getAllByIdList(List<Integer> idList) {
        return companyRepository.findAllById(idList)
                .stream()
                .map(company -> {
                    try {
                        return this.convertFromEntityToView(company);
                    } catch (ExecutionException | InterruptedException e) {
                        Thread.currentThread().interrupt(); // Preserve interrupt status
                        throw new RuntimeException("Error converting summary to view", e);
                    }
                })
                .toList();
    }

    private CompanyView convertFromEntityToView(Company company) throws ExecutionException, InterruptedException {
        CompanyMetric companyMetric = company.getCompanyMetrics()
                .stream().max(Comparator.comparing(CompanyMetric::getCompanyMetricId))
                .orElse(null);
        Objects.requireNonNull(companyMetric);
        Set<CompanyIndustry> companyIndustryList = company.getCompanyIndustries();
        Set<CompanySpeciality> companySpecialityList = company.getCompanySpecialities();

        CompanyView.CompanyViewBuilder builder = CompanyView.builder()
                .companyId(company.getCompanyId())
                .name(company.getName())
                .description(company.getDescription())
                .companySize(CompanySize.fromValue(company.getCompanySize()).getDescription())
                .address(buildAddress(AddressDto.builder()
                        .country(company.getCountry())
                        .city(company.getCity())
                        .state(company.getState())
                        .zipCode(company.getZipCode())
                        .address(company.getAddress())
                        .build()))
                .url(company.getUrl())
                .metric(CompanyMetricView.builder()
                        .employeeCount(companyMetric.getEmployeeCount())
                        .employeeCount(companyMetric.getEmployeeCount())
                        .recordOn(Objects.nonNull(companyMetric.getRecordOn())
                                ? DateTimeUtils.fromTimestamp(companyMetric.getRecordOn())
                                : null)
                        .build());

        List<Integer> cisIds = companyIndustryList
                .stream()
                .map(CompanyIndustry::getCompanyIndustryId)
                .map(CompanyIndustryId::getIndustryId)
                .sorted()
                .toList();
        CompletableFuture<List<CompanyIndustryView>> civFuture = CompletableFuture.supplyAsync(() -> companyHelper.getIndustries(cisIds));

        List<Integer> cssIds = companySpecialityList
                .stream()
                .map(CompanySpeciality::getCompanySpecialityId)
                .map(CompanySpecialityId::getSpecialityId)
                .sorted()
                .toList();
        CompletableFuture<List<CompanySpecialityView>> csvFuture = CompletableFuture.supplyAsync(() -> companyHelper.getSpecialities(cssIds));

        CompletableFuture.allOf(civFuture, csvFuture).join();
        builder.industries(civFuture.get());
        builder.specialities(csvFuture.get());

        return builder.build();
    }

    private CompanyView convertFromSummaryToView(CompanySummary summary) throws ExecutionException, InterruptedException {

        CompanyView.CompanyViewBuilder builder = CompanyView.builder()
                .companyId(summary.getCompanyId())
                .name(summary.getName())
                .description(summary.getDescription())
                .companySize(CompanySize.fromValue(summary.getCompanySize()).getDescription())
                .address(buildAddress(AddressDto.builder()
                        .country(summary.getCountry())
                        .city(summary.getCity())
                        .state(summary.getState())
                        .zipCode(summary.getZipCode())
                        .address(summary.getAddress())
                        .build()))
                .url(summary.getUrl());

        CompanyMetricSummary cms = summary.getCompanyMetrics()
                .stream().max(Comparator.comparing(CompanyMetricSummary::getCompanyMetricId)).orElse(null);
        Objects.requireNonNull(cms);
        builder
                .metric(CompanyMetricView.builder()
                        .employeeCount(cms.getEmployeeCount() == null ? 0 : cms.getEmployeeCount())
                        .followerCount(cms.getFollowerCount() == null ? 0 : cms.getFollowerCount())
                        .recordOn(Objects.nonNull(cms.getRecordOn())
                                ? DateTimeUtils.fromTimestamp(cms.getRecordOn())
                                : null)
                        .build());

        List<CompanyIndustrySummary> cisList = summary.getCompanyIndustries();
        List<Integer> cisIds = cisList
                .stream()
                .map(CompanyIndustrySummary::getIndustryId)
                .sorted()
                .toList();
        CompletableFuture<List<CompanyIndustryView>> civFuture = CompletableFuture.supplyAsync(() -> companyHelper.getIndustries(cisIds));

        List<CompanySpecialitySummary> cssList = summary.getCompanySpecialities();
        List<Integer> cssIds = cssList
                .stream()
                .map(CompanySpecialitySummary::getSpecialityId)
                .sorted()
                .toList();
        CompletableFuture<List<CompanySpecialityView>> csvFuture = CompletableFuture.supplyAsync(() -> companyHelper.getSpecialities(cssIds));

        CompletableFuture.allOf(civFuture, csvFuture).join();

        builder.industries(civFuture.get());
        builder.specialities(csvFuture.get());

        return builder.build();
    }

    private String buildAddress(AddressDto addressDto) {

        // Use StringBuilder for efficient string construction
        StringBuilder sb = new StringBuilder();

        // Add street address if present
        if (StringUtils.hasText(addressDto.getAddress())) {
            sb.append(addressDto.getAddress());
        }

        // Add city if present, with a comma if there's prior content
        if (StringUtils.hasText(addressDto.getCity())) {
            if (!sb.isEmpty()) {
                sb.append(", ");
            }
            sb.append(addressDto.getCity());
        }

        // Combine state and zip code as a unit (e.g., "IL 62704")
        String stateZip = "";
        if (StringUtils.hasText(addressDto.getState())) {
            stateZip += addressDto.getState();
        }
        if (StringUtils.hasText(addressDto.getZipCode())) {
            if (!stateZip.isEmpty()) {
                stateZip += " ";// Space between state and zip
            }
            stateZip += addressDto.getZipCode();
        }
        if (!stateZip.isEmpty()) {
            if (!sb.isEmpty()) {
                sb.append(", ");
            }
            sb.append(stateZip);
        }

        // Add country if present
        if (StringUtils.hasText(addressDto.getCountry())) {
            if (!sb.isEmpty()) {
                sb.append(", ");
            }
            sb.append(addressDto.getCountry());
        }

        return sb.toString();
    }
}