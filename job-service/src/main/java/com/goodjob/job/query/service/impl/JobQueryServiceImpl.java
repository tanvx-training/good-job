package com.goodjob.job.query.service.impl;

import com.goodjob.common.dto.ApiResponse;
import com.goodjob.common.dto.PageResponseDTO;
import com.goodjob.common.util.DateTimeUtils;
import com.goodjob.job.common.enums.EducationLevel;
import com.goodjob.job.common.enums.ExperienceLevel;
import com.goodjob.job.common.enums.JobStatus;
import com.goodjob.job.common.enums.WorkType;
import com.goodjob.job.feign.benefit.BenefitFeignClient;
import com.goodjob.job.feign.benefit.BenefitView;
import com.goodjob.job.feign.company.CompanyFeignClient;
import com.goodjob.job.feign.company.CompanyView;
import com.goodjob.job.feign.industry.IndustryFeignClient;
import com.goodjob.job.feign.industry.IndustryView;
import com.goodjob.job.feign.skill.SkillFeignClient;
import com.goodjob.job.feign.skill.SkillView;
import com.goodjob.job.query.dto.*;
import com.goodjob.job.query.service.JobQueryService;
import com.goodjob.job.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of the JobQueryService interface.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class JobQueryServiceImpl implements JobQueryService {

    private final JobRepository jobRepository;

    private final BenefitFeignClient benefitClient;

    private final CompanyFeignClient companyClient;

    private final IndustryFeignClient industryClient;

    private final SkillFeignClient skillClient;

    @Override
    public PageResponseDTO<JobView> getAllJobs(JobQuery query) {

        String[] parts = query.getSort().split(",");
        Sort sort = Sort.by(Sort.Direction.fromString(parts[1]), parts[0]);
        Pageable pageable = PageRequest.of(query.getPage(), query.getSize(), sort);

        Page<JobView> jobViewPage = jobRepository.findByDeleteFlg(false, pageable)
                .map(this::convertFromSummaryToView);

        return new PageResponseDTO<>(jobViewPage);
    }

    private Set<JobBenefitView> getBenefits(List<JobBenefitSummary> jobBenefitSummaryList) {
        List<Integer> idList = jobBenefitSummaryList.stream()
                .map(JobBenefitSummary::getBenefitId)
                .toList();
        if (!CollectionUtils.isEmpty(idList)) {
            String ids = String.join(",", idList.stream()
                    .map(String::valueOf)
                    .toList());
            ResponseEntity<ApiResponse<List<BenefitView>>> benefitResponse = benefitClient.getBatchBenefits(ids);
            if (benefitResponse.getStatusCode().is2xxSuccessful() && Objects.nonNull(benefitResponse.getBody())) {
                return benefitResponse.getBody().getData()
                        .stream()
                        .map(bv -> JobBenefitView.builder()
                                .benefitName(bv.getType())
                                .build())
                        .collect(Collectors.toSet());
            }
        }
        return Collections.emptySet();
    }

    private Set<JobSkillView> getSkills(List<JobSkillSummary> jobSkillSummaryList) {
        List<Integer> idList = jobSkillSummaryList.stream()
                .map(JobSkillSummary::getSkillId)
                .toList();
        if (!CollectionUtils.isEmpty(idList)) {
            String ids = String.join(",", idList.stream()
                    .map(String::valueOf)
                    .toList());
            ResponseEntity<ApiResponse<List<SkillView>>> skillResponse = skillClient.getBatchSkills(ids);
            if (skillResponse.getStatusCode().is2xxSuccessful() && Objects.nonNull(skillResponse.getBody())) {
                return skillResponse.getBody().getData()
                        .stream()
                        .map(sv -> JobSkillView.builder()
                                .abbreviation(sv.getAbbreviation())
                                .name(sv.getName())
                                .build())
                        .collect(Collectors.toSet());
            }
        }
        return Collections.emptySet();
    }

    private Set<JobIndustryView> getIndustries(List<JobIndustrySummary> jobIndustrySummaryList) {
        List<Integer> idList = jobIndustrySummaryList.stream()
                .map(JobIndustrySummary::getIndustryId)
                .toList();
        if (!CollectionUtils.isEmpty(idList)) {
            String ids = String.join(",", idList.stream()
                    .map(String::valueOf)
                    .toList());
            ResponseEntity<ApiResponse<List<IndustryView>>> industryResponse = industryClient.getBatchIndustries(ids);
            if (industryResponse.getStatusCode().is2xxSuccessful() && Objects.nonNull(industryResponse.getBody())) {
                return industryResponse.getBody().getData()
                        .stream()
                        .map(iv -> JobIndustryView.builder()
                                .industryName(iv.getName())
                                .build())
                        .collect(Collectors.toSet());
            }
        }
        return Collections.emptySet();
    }

    private JobCompanyView getCompany(Integer companyId) {
        JobCompanyView.JobCompanyViewBuilder companyBuilder = JobCompanyView.builder();
        if (Objects.nonNull(companyId)) {
            ResponseEntity<ApiResponse<CompanyView>> companyResponse = companyClient.getCompanyById(companyId);
            if (companyResponse.getStatusCode().is2xxSuccessful() && Objects.nonNull(companyResponse.getBody())) {
                CompanyView company = companyResponse.getBody().getData();
                companyBuilder
                        .name(company.getName())
                        .description(company.getDescription())
                        .companySize(company.getCompanySize())
                        .address(company.getAddress())
                        .url(company.getUrl());
            }
        }
        return companyBuilder.build();
    }

    private JobView convertFromSummaryToView(JobSummary summary) {
        return JobView.builder()
                .jobId(summary.getJobId())
                .company(this.getCompany(summary.getCompanyId()))
                .title(summary.getTitle())
                .description(summary.getDescription())
                .workType(WorkType.fromValue(summary.getWorkType()).getDescription())
                .educationLevel(Objects.nonNull(summary.getEducationLevel()) ? EducationLevel.fromValue(summary.getEducationLevel()).getDescription() : null)
                .experienceLevel(Objects.nonNull(summary.getExperienceLevel()) ? ExperienceLevel.fromValue(summary.getExperienceLevel()).getDescription() : null)
                .remoteAllowed(summary.getRemoteAllowed())
                .location(summary.getLocation())
                .zipCode(summary.getZipCode())
                .skillsDesc(summary.getSkillsDesc())
                .expiry(Objects.nonNull(summary.getExpiry()) ? DateTimeUtils.fromTimestamp(summary.getExpiry()) : null)
                .closedTime(Objects.nonNull(summary.getClosedTime()) ? DateTimeUtils.fromTimestamp(summary.getClosedTime()) : null)
                .jobStatus(Objects.nonNull(summary.getJobStatus()) ? JobStatus.fromValue(summary.getJobStatus()).getDescription() : null)
                .benefits(this.getBenefits(summary.getJobBenefits()))
                .skills(this.getSkills(summary.getJobSkills()))
                .industries(this.getIndustries(summary.getJobIndustries()))
                .build();
    }
}