package com.goodjob.job.domain.job.query.impl;

import com.goodjob.common.dto.response.ApiResponse;
import com.goodjob.common.dto.response.PageResponseDTO;
import com.goodjob.common.exception.ResourceNotFoundException;
import com.goodjob.common.util.DateTimeUtils;
import com.goodjob.job.domain.job.entity.Job;
import com.goodjob.job.domain.job.entity.JobBenefit;
import com.goodjob.job.domain.job.entity.JobIndustry;
import com.goodjob.job.domain.job.entity.JobSkill;
import com.goodjob.job.infrastructure.common.enums.EducationLevel;
import com.goodjob.job.infrastructure.common.enums.ExperienceLevel;
import com.goodjob.job.infrastructure.common.enums.JobStatus;
import com.goodjob.job.infrastructure.common.enums.WorkType;
import com.goodjob.job.domain.job.dto.*;
import com.goodjob.job.domain.job.repository.*;
import com.goodjob.job.infrastructure.feign.CompanyFeignClient;
import com.goodjob.job.infrastructure.feign.MetadataFeignClient;
import com.goodjob.job.infrastructure.feign.benefit.BenefitView;
import com.goodjob.job.infrastructure.feign.company.CompanyView;
import com.goodjob.job.infrastructure.feign.industry.IndustryView;
import com.goodjob.job.infrastructure.feign.skill.SkillView;
import com.goodjob.job.domain.job.query.JobQueryService;
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

    private final MetadataFeignClient metadataFeignClient;

    private final CompanyFeignClient companyFeignClient;

    @Override
    public PageResponseDTO<JobView> getAllJobs(JobQuery query) {

        String[] parts = query.getSort().split(",");
        Sort sort = Sort.by(Sort.Direction.fromString(parts[1]), parts[0]);
        Pageable pageable = PageRequest.of(query.getPage(), query.getSize(), sort);

        Page<JobView> jobViewPage = jobRepository.findByDeleteFlg(false, pageable)
                .map(this::convertFromSummaryToView);

        return new PageResponseDTO<>(jobViewPage);
    }

    @Override
    public JobView getJobById(Long id) {
        return jobRepository.findById(id)
                .map(this::convertFromEntityToView)
                .orElseThrow(() -> new ResourceNotFoundException(
                        Job.class.getName(),
                        "id",
                        id));
    }

    private JobView convertFromEntityToView(Job job) {
        return JobView.builder()
                .jobId(job.getJobId())
                .company(this.getCompany(job.getCompanyId()))
                .title(job.getTitle())
                .description(job.getDescription())
                .workType(WorkType.fromValue(job.getWorkType()).getDescription())
                .educationLevel(Objects.nonNull(job.getEducationLevel())
                        ? EducationLevel.fromValue(job.getEducationLevel()).getDescription()
                        : null)
                .experienceLevel(Objects.nonNull(job.getExperienceLevel())
                        ? ExperienceLevel.fromValue(job.getExperienceLevel()).getDescription()
                        : null)
                .remoteAllowed(job.getRemoteAllowed())
                .location(job.getLocation())
                .zipCode(job.getZipCode())
                .skillsDesc(job.getSkillsDesc())
                .expiry(Objects.nonNull(job.getExpiry())
                        ? DateTimeUtils.fromTimestamp(job.getExpiry())
                        : null)
                .closedTime(Objects.nonNull(job.getClosedTime())
                        ? DateTimeUtils.fromTimestamp(job.getClosedTime())
                        : null)
                .jobStatus(Objects.nonNull(job.getJobStatus())
                        ? JobStatus.fromValue(job.getJobStatus()).getDescription()
                        : null)
                .benefits(this.getBenefits(job.getJobBenefits()
                        .stream()
                        .map(JobBenefit::getBenefitId)
                        .toList()))
                .skills(this.getSkills(job.getJobSkills()
                        .stream()
                        .map(JobSkill::getSkillId)
                        .toList()))
                .industries(this.getIndustries(job.getJobIndustries()
                        .stream()
                        .map(JobIndustry::getIndustryId)
                        .toList()))
                .build();
    }

    private Set<JobBenefitView> getBenefits(List<Integer> idList) {
        if (!CollectionUtils.isEmpty(idList)) {
            String ids = String.join(",", idList.stream()
                    .map(String::valueOf)
                    .toList());
            ResponseEntity<ApiResponse<List<BenefitView>>> benefitResponse = metadataFeignClient.getBatchBenefits(ids);
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

    private Set<JobSkillView> getSkills(List<Integer> idList) {
        if (!CollectionUtils.isEmpty(idList)) {
            String ids = String.join(",", idList.stream()
                    .map(String::valueOf)
                    .toList());
            ResponseEntity<ApiResponse<List<SkillView>>> skillResponse = metadataFeignClient.getBatchSkills(ids);
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

    private Set<JobIndustryView> getIndustries(List<Integer> idList) {
        if (!CollectionUtils.isEmpty(idList)) {
            String ids = String.join(",", idList.stream()
                    .map(String::valueOf)
                    .toList());
            ResponseEntity<ApiResponse<List<IndustryView>>> industryResponse = metadataFeignClient.getBatchIndustries(ids);
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
            ResponseEntity<ApiResponse<CompanyView>> companyResponse = companyFeignClient.getCompanyById(companyId);
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
                .benefits(this.getBenefits(summary.getJobBenefits().stream()
                        .map(JobBenefitSummary::getBenefitId)
                        .toList()))
                .skills(this.getSkills(summary.getJobSkills()
                        .stream()
                        .map(JobSkillSummary::getSkillId)
                        .toList()))
                .industries(this.getIndustries(summary.getJobIndustries()
                        .stream()
                        .map(JobIndustrySummary::getIndustryId)
                        .toList()))
                .build();
    }
}