package com.goodjob.job.domain.job.query.impl;

import com.goodjob.common.dto.response.PageResponseDTO;
import com.goodjob.common.exception.ResourceNotFoundException;
import com.goodjob.common.util.DateTimeUtils;
import com.goodjob.job.application.helper.JobHelper;
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
import com.goodjob.job.domain.job.query.JobQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Implementation of the JobQueryService interface.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class JobQueryServiceImpl implements JobQueryService {

    private final JobRepository jobRepository;

    private final JobHelper jobHelper;

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
                .company(jobHelper.getCompany(job.getCompanyId()))
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
                .benefits(jobHelper.getBenefits(job.getJobBenefits()
                        .stream()
                        .map(JobBenefit::getBenefitId)
                        .toList()))
                .skills(jobHelper.getSkills(job.getJobSkills()
                        .stream()
                        .map(JobSkill::getSkillId)
                        .toList()))
                .industries(jobHelper.getIndustries(job.getJobIndustries()
                        .stream()
                        .map(JobIndustry::getIndustryId)
                        .toList()))
                .build();
    }

    private JobView convertFromSummaryToView(JobSummary summary) {
        return JobView.builder()
                .jobId(summary.getJobId())
                .company(jobHelper.getCompany(summary.getCompanyId()))
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
                .views(summary.getViews())
                .applies(summary.getApplies())
                .benefits(jobHelper.getBenefits(summary.getJobBenefits().stream()
                        .map(JobBenefitSummary::getBenefitId)
                        .toList()))
                .skills(jobHelper.getSkills(summary.getJobSkills()
                        .stream()
                        .map(JobSkillSummary::getSkillId)
                        .toList()))
                .industries(jobHelper.getIndustries(summary.getJobIndustries()
                        .stream()
                        .map(JobIndustrySummary::getIndustryId)
                        .toList()))
                .build();
    }
}