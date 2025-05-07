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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

import java.math.BigDecimal;

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
    @Cacheable(value = "jobs", key = "#query.hashCode()", unless = "#result.content.isEmpty()")
    public PageResponseDTO<JobView> getAllJobs(JobQuery query) {

        String[] parts = query.getSort().split(",");
        Sort sort = Sort.by(Sort.Direction.fromString(parts[1]), parts[0]);
        Pageable pageable = PageRequest.of(query.getPage(), query.getSize(), sort);

        Page<JobSummary> jobSummaryPage = jobRepository.findByDeleteFlg(false, pageable);

        List<Integer> companyIds = jobSummaryPage.getContent().stream()
                .map(JobSummary::getCompanyId)
                .distinct()
                .toList();
        List<JobCompanyView> companies = jobHelper.getBatchCompanies(companyIds);
        Map<Integer, JobCompanyView> companyMap = companies.stream()
                .collect(
                        Collectors.toMap(
                                JobCompanyView::getId,
                                Function.identity()
                        )
                );
        List<JobView> jobViews = jobSummaryPage.getContent().stream()
                .map(summary -> {
                    try {
                        return this.convertFromSummaryToView(summary, companyMap);
                    } catch (ExecutionException | InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("Error converting summary to view", e);
                    }
                })
                .toList();
        Page<JobView> jobViewPage = new PageImpl<>(jobViews, pageable, jobSummaryPage.getTotalElements());
        return new PageResponseDTO<>(jobViewPage);
    }

    @Override
    @Cacheable(value = "jobs", key = "#id.toString()", unless = "#result == null")
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

    private JobView convertFromSummaryToView(JobSummary summary, Map<Integer, JobCompanyView> companyMap)
            throws ExecutionException, InterruptedException {
        JobCompanyView jcv = companyMap.getOrDefault(summary.getCompanyId(), JobCompanyView.builder().build());

        CompletableFuture<List<JobBenefitView>> jbvFuture = CompletableFuture.supplyAsync(() ->
                jobHelper.getBenefits(summary.getJobBenefits().stream()
                        .map(JobBenefitSummary::getBenefitId)
                        .distinct()
                        .sorted()
                        .toList()));
        CompletableFuture<List<JobSkillView>> jsvFuture = CompletableFuture.supplyAsync(() ->
                jobHelper.getSkills(summary.getJobSkills().stream()
                        .map(JobSkillSummary::getSkillId)
                        .distinct()
                        .sorted()
                        .toList()));
        CompletableFuture<List<JobIndustryView>> jivFuture = CompletableFuture.supplyAsync(() ->
                jobHelper.getIndustries(summary.getJobIndustries().stream()
                        .map(JobIndustrySummary::getIndustryId)
                        .distinct()
                        .sorted()
                        .toList()));

        CompletableFuture.allOf(jbvFuture, jsvFuture, jivFuture).join();

        return JobView.builder()
                .jobId(summary.getJobId())
                .company(jcv)
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

    public Page<JobSummary> getJobSummaries(boolean deleteFlg, Pageable pageable) {
        int limit = pageable.getPageSize();
        int offset = pageable.getPageNumber() * pageable.getPageSize();

        List<Object[]> rows = jobRepository.findJobSummaries(deleteFlg, limit, offset);
        Map<Long, JobSummaryImpl> jobMap = new HashMap<>();

        for (Object[] row : rows) {
            Long jobId = Objects.isNull(row[0]) ? 0L : ((Number) row[0]).longValue();
            JobSummaryImpl jobSummary = jobMap.get(jobId);

            if (jobSummary == null) {
                jobSummary = new JobSummaryImpl();
                jobSummary.setJobId(jobId);
                jobSummary.setCompanyId(Objects.isNull(row[1]) ? 0 : (Integer) row[1]);
                jobSummary.setTitle(Objects.isNull(row[2]) ? "" : (String) row[2]);
                jobSummary.setDescription(Objects.isNull(row[3]) ? "" : (String) row[3]);
                jobSummary.setWorkType(Objects.isNull(row[4]) ? 0 : (Integer) row[4]);
                jobSummary.setEducationLevel(Objects.isNull(row[5]) ? 0 : (Integer) row[5]);
                jobSummary.setExperienceLevel(Objects.isNull(row[6]) ? 0 : (Integer) row[6]);
                jobSummary.setRemoteAllowed(!Objects.isNull(row[7]) && (Boolean) row[7]);
                jobSummary.setLocation(Objects.isNull(row[8]) ? "" : (String) row[8]);
                jobSummary.setZipCode(Objects.isNull(row[9]) ? "" : (String) row[9]);
                jobSummary.setSkillsDesc(Objects.isNull(row[10]) ? "" : (String) row[10]);
                jobSummary.setExpiry(Objects.isNull(row[11]) ? null : ((Number) row[11]).longValue());
                jobSummary.setClosedTime(Objects.isNull(row[12]) ? null : ((Number) row[12]).longValue());
                jobSummary.setJobStatus(Objects.isNull(row[13]) ? 0 : (Integer) row[13]);
                jobSummary.setViews(Objects.isNull(row[14]) ? 0 : (Integer) row[14]);
                jobSummary.setApplies(Objects.isNull(row[15]) ? 0 : (Integer) row[15]);
                jobSummary.setJobBenefits(new ArrayList<>());
                jobSummary.setJobSkills(new ArrayList<>());
                jobSummary.setJobIndustries(new ArrayList<>());
                jobMap.put(jobId, jobSummary);
            }

            // Xử lý JobSalary
            if (!Objects.isNull(row[16])) {
                JobSalarySummaryImpl salary = new JobSalarySummaryImpl();
                salary.setSalaryId((Integer) row[16]);
                salary.setMinSalary(Objects.isNull(row[17]) ? BigDecimal.ZERO : (BigDecimal) row[17]);
                salary.setMedSalary(Objects.isNull(row[18]) ? BigDecimal.ZERO : (BigDecimal) row[18]);
                salary.setMaxSalary(Objects.isNull(row[19]) ? BigDecimal.ZERO : (BigDecimal) row[19]);
                salary.setPayPeriod(Objects.isNull(row[20]) ? 0 : (Integer) row[20]);
                salary.setCurrency(Objects.isNull(row[21]) ? "" : (String) row[21]);
                salary.setCompensationType(Objects.isNull(row[22]) ? 0 : (Integer) row[22]);
                jobSummary.setJobSalary(salary);
            }

            // Xử lý JobBenefit
            if (!Objects.isNull(row[23])) {
                JobBenefitSummaryImpl benefit = new JobBenefitSummaryImpl();
                benefit.setBenefitId((Integer) row[23]);
                jobSummary.getJobBenefits().add(benefit);
            }

            // Xử lý JobSkill
            if (!Objects.isNull(row[24])) {
                JobSkillSummaryImpl skill = new JobSkillSummaryImpl();
                skill.setSkillId((Integer) row[24]);
                jobSummary.getJobSkills().add(skill);
            }

            // Xử lý JobIndustry
            if (!Objects.isNull(row[25])) {
                JobIndustrySummaryImpl industry = new JobIndustrySummaryImpl();
                industry.setIndustryId((Integer) row[25]);
                jobSummary.getJobIndustries().add(industry);
            }
        }

        List<JobSummary> jobSummaries = new ArrayList<>(jobMap.values());
        long total = jobRepository.countByDeleteFlg(deleteFlg);
        return new PageImpl<>(jobSummaries, pageable, total);
    }
}