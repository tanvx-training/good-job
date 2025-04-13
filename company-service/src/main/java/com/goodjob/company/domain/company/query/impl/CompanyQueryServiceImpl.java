package com.goodjob.company.domain.company.query.impl;

import com.goodjob.common.dto.response.ApiResponse;
import com.goodjob.common.dto.response.PageResponseDTO;
import com.goodjob.common.util.DateTimeUtils;
import com.goodjob.company.infrastructure.common.dto.AddressDto;
import com.goodjob.company.infrastructure.common.enums.CompanySize;
import com.goodjob.company.domain.company.entity.Company;
import com.goodjob.company.domain.company.entity.CompanyIndustry;
import com.goodjob.company.domain.company.entity.CompanyMetric;
import com.goodjob.company.domain.company.entity.CompanySpeciality;
import com.goodjob.company.domain.company.entity.id.CompanyIndustryId;
import com.goodjob.company.domain.company.entity.id.CompanySpecialityId;
import com.goodjob.company.application.exception.CompanyNotFoundException;
import com.goodjob.company.infrastructure.feign.MetadataFeignClient;
import com.goodjob.company.infrastructure.feign.industry.IndustryView;
import com.goodjob.company.infrastructure.feign.speciality.SpecialityView;
import com.goodjob.company.domain.company.dto.CompanyIndustryView;
import com.goodjob.company.domain.company.dto.CompanyMetricView;
import com.goodjob.company.domain.company.dto.CompanyQuery;
import com.goodjob.company.domain.company.dto.CompanySpecialityView;
import com.goodjob.company.domain.company.dto.CompanyView;
import com.goodjob.company.domain.company.query.CompanyQueryService;
import com.goodjob.company.domain.company.repository.CompanyIndustrySummary;
import com.goodjob.company.domain.company.repository.CompanyMetricSummary;
import com.goodjob.company.domain.company.repository.CompanyRepository;
import com.goodjob.company.domain.company.repository.CompanySpecialitySummary;
import com.goodjob.company.domain.company.repository.CompanySummary;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

  private final MetadataFeignClient metadataFeignClient;

  @Override
  public PageResponseDTO<CompanyView> getAllCompanies(CompanyQuery query) {

    String[] parts = query.getSort().split(",");
    Sort sort = Sort.by(Sort.Direction.fromString(parts[1]), parts[0]);
    Pageable pageable = PageRequest.of(query.getPage(), query.getSize(), sort);

    Page<CompanyView> companyViewPage = companyRepository.findByDeleteFlg(false, pageable)
        .map(summary -> {
          log.info("Summary={}", summary);
          return this.convertFromSummaryToView(summary);
        });
    return new PageResponseDTO<>(companyViewPage);
  }

  @Override
  public CompanyView getCompanyById(Integer id) {
    return companyRepository.findById(id)
            .map(this::convertFromEntityToView)
            .orElseThrow(() -> new CompanyNotFoundException(id));
  }

  private CompanyView convertFromEntityToView(Company company) {
    CompanyMetric companyMetric = company.getCompanyMetric();
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
            .toList();
    if (!CollectionUtils.isEmpty(cisIds)) {
      String cisIdParam = String.join(",",
              cisIds.stream().map(String::valueOf).toList());
      ResponseEntity<ApiResponse<List<IndustryView>>> industryResponse = metadataFeignClient.getBatchIndustries(
              cisIdParam);
      if (industryResponse.getStatusCode().is2xxSuccessful() && Objects.nonNull(
              industryResponse.getBody())) {
        List<IndustryView> industryViews = industryResponse.getBody().getData();

        builder
                .industries(industryViews
                        .stream()
                        .map(industryView ->
                                CompanyIndustryView.builder().industryName(industryView.getName()).build())
                        .toList()
                );
      }
    }

    List<Integer> cssIds = companySpecialityList
            .stream()
            .map(CompanySpeciality::getCompanySpecialityId)
            .map(CompanySpecialityId::getSpecialityId)
            .toList();
    if (!CollectionUtils.isEmpty(cssIds)) {
      String cssIdParam = String.join(",",
              cssIds.stream().map(String::valueOf).toList());
      ResponseEntity<ApiResponse<List<SpecialityView>>> specialityResponse = metadataFeignClient.getBatchSpecialities(
              cssIdParam);
      if (specialityResponse.getStatusCode().is2xxSuccessful() && Objects.nonNull(
              specialityResponse.getBody())) {
        List<SpecialityView> specialityViews = specialityResponse.getBody().getData();

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

  private CompanyView convertFromSummaryToView(CompanySummary summary) {

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
      ResponseEntity<ApiResponse<List<IndustryView>>> industryResponse = metadataFeignClient.getBatchIndustries(
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
      ResponseEntity<ApiResponse<List<SpecialityView>>> specialityResponse = metadataFeignClient.getBatchSpecialities(
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