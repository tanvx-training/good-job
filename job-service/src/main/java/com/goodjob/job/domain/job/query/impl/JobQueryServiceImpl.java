package com.goodjob.job.domain.job.query.impl;

import com.goodjob.common.application.dto.response.PageResponseDTO;
import com.goodjob.common.application.exception.ResourceNotFoundException;
import com.goodjob.common.infrastructure.util.DateTimeUtils;
import com.goodjob.job.infrastructure.helper.JobHelper;
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

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
                .map((jobSummary -> {
                    try {
                        return this.convertFromSummaryToView(jobSummary);
                    } catch (ExecutionException | InterruptedException e) {
                        Thread.currentThread().interrupt(); // Preserve interrupt status
                        throw new RuntimeException("Error converting summary to view", e);
                    }
                }));

        return new PageResponseDTO<>(jobViewPage);
    }

    @Override
    public JobView getJobById(Long id) {
        return jobRepository.findById(id)
                .map(job -> {
                    try {
                        return this.convertFromEntityToView(job);
                    } catch (ExecutionException | InterruptedException e) {
                        Thread.currentThread().interrupt(); // Preserve interrupt status
                        throw new RuntimeException("Error converting summary to view", e);
                    }
                })
                .orElseThrow(() -> new ResourceNotFoundException(Job.class.getName(), "ID", id));
    }

    private JobView convertFromEntityToView(Job job) throws ExecutionException, InterruptedException {
        CompletableFuture<JobCompanyView> jcvFuture = CompletableFuture.supplyAsync(() -> jobHelper.getCompany(job.getCompanyId()));
        CompletableFuture<List<JobBenefitView>> jbvFuture = CompletableFuture.supplyAsync(() -> jobHelper.getBenefits(job.getJobBenefits()
                .stream()
                .map(JobBenefit::getBenefitId)
                .sorted()
                .toList()));
        CompletableFuture<List<JobSkillView>> jsvFuture = CompletableFuture.supplyAsync(() -> jobHelper.getSkills(job.getJobSkills()
                .stream()
                .map(JobSkill::getSkillId)
                .sorted()
                .toList()));
        CompletableFuture<List<JobIndustryView>> jivFuture = CompletableFuture.supplyAsync(() -> jobHelper.getIndustries(job.getJobIndustries()
                .stream()
                .map(JobIndustry::getIndustryId)
                .sorted()
                .toList()));
        CompletableFuture.allOf(jcvFuture, jbvFuture, jsvFuture, jivFuture).join();
        return JobView.builder()
                .jobId(job.getJobId())
                .company(jcvFuture.get())
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
                .benefits(jbvFuture.get())
                .skills(jsvFuture.get())
                .industries(jivFuture.get())
                .build();
    }

    private JobView convertFromSummaryToView(JobSummary summary) throws ExecutionException, InterruptedException {
        CompletableFuture<JobCompanyView> jcvFuture = CompletableFuture.supplyAsync(() -> jobHelper.getCompany(summary.getCompanyId()));
        CompletableFuture<List<JobBenefitView>> jbvFuture = CompletableFuture.supplyAsync(() -> jobHelper.getBenefits(summary.getJobBenefits()
                .stream()
                .map(JobBenefitSummary::getBenefitId)
                .sorted()
                .toList()));
        CompletableFuture<List<JobSkillView>> jsvFuture = CompletableFuture.supplyAsync(() -> jobHelper.getSkills(summary.getJobSkills()
                .stream()
                .map(JobSkillSummary::getSkillId)
                .sorted()
                .toList()));
        CompletableFuture<List<JobIndustryView>> jivFuture = CompletableFuture.supplyAsync(() -> jobHelper.getIndustries(summary.getJobIndustries()
                .stream()
                .map(JobIndustrySummary::getIndustryId)
                .sorted()
                .toList()));
        CompletableFuture.allOf(jcvFuture, jbvFuture, jsvFuture, jivFuture).join();
        return JobView.builder()
                .jobId(summary.getJobId())
                .company(jcvFuture.get())
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
                .benefits(jbvFuture.get())
                .skills(jsvFuture.get())
                .industries(jivFuture.get())
                .build();
    }
}