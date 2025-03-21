package com.goodjob.company.query.service.impl;

import com.goodjob.common.dto.ApiResponse;
import com.goodjob.common.dto.PageResponseDTO;
import com.goodjob.common.util.DateTimeUtils;
import com.goodjob.company.common.enums.CompanySize;
import com.goodjob.company.feign.industry.IndustryFeignClient;
import com.goodjob.company.feign.industry.IndustryView;
import com.goodjob.company.feign.speciality.SpecialityFeignClient;
import com.goodjob.company.feign.speciality.SpecialityView;
import com.goodjob.company.query.dto.CompanyIndustryView;
import com.goodjob.company.query.dto.CompanyMetricView;
import com.goodjob.company.query.dto.CompanyQuery;
import com.goodjob.company.query.dto.CompanySpecialityView;
import com.goodjob.company.query.dto.CompanyView;
import com.goodjob.company.query.service.CompanyQueryService;
import com.goodjob.company.repository.CompanyIndustrySummary;
import com.goodjob.company.repository.CompanyMetricSummary;
import com.goodjob.company.repository.CompanyRepository;
import com.goodjob.company.repository.CompanySpecialitySummary;
import com.goodjob.company.repository.CompanySummary;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * Implementation of the CompanyQueryService interface.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyQueryServiceImpl implements CompanyQueryService {

  private final CompanyRepository companyRepository;

  private final IndustryFeignClient industryFeignClient;

  private final SpecialityFeignClient specialityFeignClient;

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

    CompanyView.CompanyViewBuilder builder = CompanyView.builder()
        .companyId(summary.getCompanyId())
        .name(summary.getName())
        .description(summary.getDescription())
        .companySize(CompanySize.fromValue(summary.getCompanySize()).getDescription())
        .address(buildAddress(summary))
        .url(summary.getUrl());

    CompanyMetricSummary cms = summary.getCompanyMetric();
    builder
        .metric(CompanyMetricView.builder()
            .employeeCount(cms.getEmployeeCount())
            .followerCount(cms.getFollowerCount())
            .recordOn(Objects.nonNull(cms.getRecordOn())
                ? DateTimeUtils.fromTimestamp(cms.getRecordOn())
                : null)
            .build());

    List<CompanyIndustrySummary> cisList = summary.getCompanyIndustries();
    List<Integer> cisIds = cisList
        .stream()
        .map(CompanyIndustrySummary::getIndustryId)
        .toList();
    if (!CollectionUtils.isEmpty(cisIds)) {
      String cisIdParam = String.join(",",
          cisIds.stream().map(String::valueOf).toList());
      ResponseEntity<ApiResponse<List<IndustryView>>> industryResponse = industryFeignClient.getBatchIndustries(
          cisIdParam);
      if (industryResponse.getStatusCode().is2xxSuccessful() && Objects.nonNull(
          industryResponse.getBody())) {
        List<IndustryView> industryViews = industryResponse.getBody().getData();
        log.info("IndustryViews={}", industryViews);
        builder
            .industries(industryViews
                .stream()
                .map(industryView ->
                    CompanyIndustryView.builder().industryName(industryView.getName()).build())
                .toList()
            );
      }
    }

    List<CompanySpecialitySummary> cssList = summary.getCompanySpecialities();
    List<Integer> cssIds = cssList
        .stream()
        .map(CompanySpecialitySummary::getSpecialityId)
        .toList();
    if (!CollectionUtils.isEmpty(cssIds)) {
      String cssIdParam = String.join(",",
          cssIds.stream().map(String::valueOf).toList());
      ResponseEntity<ApiResponse<List<SpecialityView>>> specialityResponse = specialityFeignClient.getBatchSpecialities(
          cssIdParam);
      if (specialityResponse.getStatusCode().is2xxSuccessful() && Objects.nonNull(
          specialityResponse.getBody())) {
        List<SpecialityView> specialityViews = specialityResponse.getBody().getData();
        log.info("SpecialityViews={}", specialityViews);
        builder
            .specialities(specialityViews
                .stream()
                .map(specialityView ->
                    CompanySpecialityView.builder().name(specialityView.getName()).build())
                .toList());
      }
    }

    return builder.build();
  }

  private String buildAddress(CompanySummary summary) {

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