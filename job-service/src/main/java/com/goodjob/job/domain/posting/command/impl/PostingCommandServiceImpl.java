package com.goodjob.job.domain.posting.command.impl;

import com.goodjob.common.enums.BaseEnum;
import com.goodjob.common.util.DateTimeUtils;
import com.goodjob.job.application.MessageProperties;
import com.goodjob.job.application.helper.JobHelper;
import com.goodjob.job.application.helper.PostingHelper;
import com.goodjob.job.domain.job.dto.JobBenefitView;
import com.goodjob.job.domain.job.dto.JobCompanyView;
import com.goodjob.job.domain.job.dto.JobIndustryView;
import com.goodjob.job.domain.job.dto.JobSkillView;
import com.goodjob.job.domain.job.entity.*;
import com.goodjob.job.domain.job.repository.JobRepository;
import com.goodjob.job.domain.posting.command.PostingCommandService;
import com.goodjob.job.domain.posting.dto.CreatePostingCommand;
import com.goodjob.job.domain.posting.entity.JobPosting;
import com.goodjob.job.domain.posting.repository.JobPostingRepository;
import com.goodjob.job.infrastructure.common.enums.*;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PostingCommandServiceImpl implements PostingCommandService {

    private static final Integer COMMON_LENGTH = 255;

    private final MessageSource messageSource;

    private final JobRepository jobRepository;

    private final JobHelper jobHelper;

    private final PostingHelper postingHelper;

    private final JobPostingRepository jobPostingRepository;

    @Override
    public Long createPostingJob(CreatePostingCommand command) {
        // Init map error
        Map<String, List<String>> errors = new LinkedHashMap<>();

        Optional<Job> jobOptional = jobRepository.findById(command.getJobId());
        if (jobOptional.isEmpty()) {
            addError(errors, MessageProperties.JobField.JOB_ID, MessageProperties.Posting.JOB_ID_INVALID);
        } else {
            Job job = jobOptional.get();
            validateJobBasic(job, errors);
            validateJobSalary(job.getJobSalary(), errors);
            validateJobBenefits(job.getJobBenefits(), errors);
            validateJobSkills(job.getJobSkills(), errors);
            validateJobIndustries(job.getJobIndustries(), errors);
            validateCompany(job.getCompanyId(), errors);

            if (!CollectionUtils.isEmpty(errors)) {
                String description = postingHelper.generateJobDescription(job);
                JobPosting jobPosting = job.getJobPosting();
                if (Objects.isNull(jobPosting)) {
                    jobPosting = JobPosting.builder()
                            .description(description)
                            .views(0)
                            .applies(0)
                            .postingDomain("https://www.linkedin.com/")
                            .jobPostingUrl("https://www.linkedin.com/")
                            .applicationUrl("https://www.linkedin.com/")
                            .applicationType("ComplexOnsiteApply")
                            .sponsored(Boolean.FALSE)
                            .fips("")
                            .postingStatus(PostingStatus.ACTIVE.getCode())
                            .originalListedTime(DateTimeUtils.toTimestamp(command.getStartTime()))
                            .listedTime(DateTimeUtils.toTimestamp(command.getEndTime()))
                            .createdOn(LocalDateTime.now())
                            .createdBy("Schedule")
                            .lastModifiedOn(LocalDateTime.now())
                            .lastModifiedBy("Schedule")
                            .build();
                } else {
                    jobPosting.setDescription(description);
                    jobPosting.setPostingStatus(PostingStatus.ACTIVE.getCode());
                    jobPosting.setCreatedOn(LocalDateTime.now());
                    jobPosting.setCreatedBy("Schedule");
                    jobPosting.setLastModifiedOn(LocalDateTime.now());
                    jobPosting.setLastModifiedBy("Schedule");
                }
                jobPostingRepository.save(jobPosting);
                return 1L;
            }
        }

        return 0L;
    }

    private void validateJobBasic(Job job, Map<String, List<String>> errors) {
        // title
        if (!StringUtils.hasText(job.getTitle())) {
            addError(errors, MessageProperties.JobField.TITLE, MessageProperties.Posting.TITLE_REQUIRED);
        } else {
            if (job.getTitle().trim().length() > COMMON_LENGTH) {
                addError(errors, MessageProperties.JobField.TITLE, MessageProperties.Posting.TITLE_TOO_LONG);
            }
        }
        // description
        if (!StringUtils.hasText(job.getDescription())) {
            addError(errors, MessageProperties.JobField.DESCRIPTION, MessageProperties.Posting.DESCRIPTION_REQUIRED);
        }
        // work type
        WorkType workType = BaseEnum.fromValueNullable(WorkType.class, job.getWorkType());
        if (Objects.isNull(workType)) {
            addError(errors, MessageProperties.JobField.WORK_TYPE, MessageProperties.Posting.WORK_TYPE_INVALID);
        }
        // education level
        EducationLevel educationLevel = BaseEnum.fromValueNullable(EducationLevel.class, job.getEducationLevel());
        if (Objects.isNull(educationLevel)) {
            addError(errors, MessageProperties.JobField.EDUCATION_LEVEL, MessageProperties.Posting.EDUCATION_LEVEL_INVALID);
        }
        // experience level
        ExperienceLevel experienceLevel = BaseEnum.fromValueNullable(ExperienceLevel.class, job.getExperienceLevel());
        if (Objects.isNull(experienceLevel)) {
            addError(errors, MessageProperties.JobField.EXPERIENCE_LEVEL, MessageProperties.Posting.EXPERIENCE_LEVEL_INVALID);
        }
        // remote and location
        if (!Boolean.TRUE.equals(job.getRemoteAllowed()) && !StringUtils.hasText(job.getLocation())) {
            addError(errors, MessageProperties.JobField.LOCATION, MessageProperties.Posting.LOCATION_REQUIRED);
        }
        // zip code
        if (!StringUtils.hasText(job.getZipCode())) {
            addError(errors, MessageProperties.JobField.ZIP_CODE, MessageProperties.Posting.ZIP_CODE_INVALID);
        }
        // skill describe
        if (!StringUtils.hasText(job.getSkillsDesc())) {
            addError(errors, MessageProperties.JobField.SKILL_DESC, MessageProperties.Posting.SKILL_DESC_REQUIRED);
        }
        // expiry
        if (Objects.isNull(job.getExpiry())) {
            addError(errors, MessageProperties.JobField.EXPIRY, MessageProperties.Posting.EXPIRY_REQUIRED);
        } else {
            LocalDateTime expiryTime = DateTimeUtils.fromTimestamp(job.getExpiry());
            if (!expiryTime.isAfter(LocalDateTime.now())) {
                addError(errors, MessageProperties.JobField.EXPIRY, MessageProperties.Posting.EXPIRY_INVALID);
            }
        }
        // job status
        JobStatus jobStatus = BaseEnum.fromValueNullable(JobStatus.class, job.getJobStatus());
        if (Objects.isNull(jobStatus)) {
            addError(errors, MessageProperties.JobField.JOB_STATUS, MessageProperties.Posting.JOB_STATUS_INVALID);
        }
    }

    private void validateJobSalary(JobSalary jobSalary, Map<String, List<String>> errors) {
        if (Objects.isNull(jobSalary)) {
            addError(errors, MessageProperties.JobSalaryField.JOB_SALARY, MessageProperties.Posting.JOB_SALARY_INVALID);
        }
        // currency
        CurrencySalaryRange currencySalaryRange = CurrencySalaryRange.fromValue(jobSalary.getCurrency());
        if (Objects.isNull(currencySalaryRange)) {
            addError(errors, MessageProperties.JobSalaryField.CURRENCY, MessageProperties.Posting.CURRENCY_INVALID);
        }
        // min salary
        if (Objects.isNull(jobSalary.getMinSalary())) {
            addError(errors, MessageProperties.JobSalaryField.MIN_SALARY, MessageProperties.Posting.MIN_SALARY_REQUIRED);
        } else {
            if (currencySalaryRange.getMin().compareTo(jobSalary.getMinSalary()) > 0) {
                addError(errors, MessageProperties.JobSalaryField.MIN_SALARY, MessageProperties.Posting.MIN_SALARY_INVALID);
            }
        }
        // max salary
        if (Objects.isNull(jobSalary.getMaxSalary())) {
            addError(errors, MessageProperties.JobSalaryField.MAX_SALARY, MessageProperties.Posting.MAX_SALARY_REQUIRED);
        } else {
            if (currencySalaryRange.getMax().compareTo(jobSalary.getMaxSalary()) < 0) {
                addError(errors, MessageProperties.JobSalaryField.MAX_SALARY, MessageProperties.Posting.MAX_SALARY_INVALID);
            } else if (jobSalary.getMinSalary().compareTo(jobSalary.getMaxSalary()) > 0) {
                addError(errors, MessageProperties.JobSalaryField.MAX_SALARY, MessageProperties.Posting.MAX_SALARY_LESS_THAN_MIN);
            }
        }
        // med salary
        if (Objects.nonNull(jobSalary.getMedSalary()) &&
                jobSalary.getMaxSalary().compareTo(jobSalary.getMedSalary()) < 0 &&
                jobSalary.getMinSalary().compareTo(jobSalary.getMedSalary()) > 0) {
            addError(errors, MessageProperties.JobSalaryField.MED_SALARY, MessageProperties.Posting.MED_SALARY_INVALID);
        }
        // pay period
        PayPeriod payPeriod = PayPeriod.fromValue(jobSalary.getPayPeriod());
        if (Objects.isNull(payPeriod)) {
            addError(errors, MessageProperties.JobSalaryField.PAY_PERIOD, MessageProperties.Posting.PAY_PERIOD_INVALID);
        }
        // compensation type
        CompensationType compensationType = CompensationType.fromValue(jobSalary.getCompensationType());
        if (Objects.isNull(compensationType)) {
            addError(errors, MessageProperties.JobSalaryField.COMPENSATION_TYPE, MessageProperties.Posting.COMPENSATION_TYPE_INVALID);
        }
    }

    private void validateJobBenefits(Set<JobBenefit> jobBenefits, Map<String, List<String>> errors) {
        if (CollectionUtils.isEmpty(jobBenefits)) {
            addError(errors, MessageProperties.JobField.JOB_BENEFITS, MessageProperties.Posting.JOB_BENEFITS_REQUIRED);
        } else {
            Set<JobBenefitView> jobBenefitViews = jobHelper.getBenefits(jobBenefits.stream()
                    .map(JobBenefit::getBenefitId)
                    .toList());
            if (jobBenefits.size() != jobBenefitViews.size()) {
                addError(errors, MessageProperties.JobField.JOB_BENEFITS, MessageProperties.Posting.JOB_BENEFITS_INVALID);
            }
        }
    }

    private void validateJobSkills(Set<JobSkill> jobSkills, Map<String, List<String>> errors) {
        if (CollectionUtils.isEmpty(jobSkills)) {
            addError(errors, MessageProperties.JobField.JOB_SKILLS, MessageProperties.Posting.JOB_SKILLS_REQUIRED);
        } else {
            Set<JobSkillView> jobSkillViews = jobHelper.getSkills(jobSkills.stream()
                    .map(JobSkill::getSkillId)
                    .toList());
            if (jobSkills.size() != jobSkillViews.size()) {
                addError(errors, MessageProperties.JobField.JOB_SKILLS, MessageProperties.Posting.JOB_SKILLS_INVALID);
            }
        }
    }

    private void validateJobIndustries(Set<JobIndustry> jobIndustries, Map<String, List<String>> errors) {
        if (CollectionUtils.isEmpty(jobIndustries)) {
            addError(errors, MessageProperties.JobField.JOB_INDUSTRIES, MessageProperties.Posting.JOB_INDUSTRIES_REQUIRED);
        } else {
            Set<JobIndustryView> jobIndustryViews = jobHelper.getIndustries(jobIndustries.stream()
                    .map(JobIndustry::getIndustryId)
                    .toList());
            if (jobIndustries.size() != jobIndustryViews.size()) {
                addError(errors, MessageProperties.JobField.JOB_INDUSTRIES, MessageProperties.Posting.JOB_INDUSTRIES_INVALID);
            }
        }
    }

    private void validateCompany(Integer companyId, Map<String, List<String>> errors) {
        if (Objects.isNull(companyId)) {
            addError(errors, MessageProperties.CompanyField.COMPANY_ID, MessageProperties.Posting.COMPANY_ID_REQUIRED);
        } else {
            JobCompanyView jobCompanyView = jobHelper.getCompany(companyId);
            if (Objects.isNull(jobCompanyView)) {
                addError(errors, MessageProperties.CompanyField.COMPANY_ID, MessageProperties.Posting.COMPANY_ID_INVALID);
            } else {
                if (!StringUtils.hasText(jobCompanyView.getName())) {
                    addError(errors, MessageProperties.CompanyField.NAME, MessageProperties.Posting.COMPANY_NAME_REQUIRED);
                }
                if (!StringUtils.hasText(jobCompanyView.getDescription())) {
                    addError(errors, MessageProperties.CompanyField.DESCRIPTION, MessageProperties.Posting.COMPANY_DESCRIPTION_REQUIRED);
                }
                if (!StringUtils.hasText(jobCompanyView.getCompanySize())) {
                    addError(errors, MessageProperties.CompanyField.COMPANY_SIZE, MessageProperties.Posting.COMPANY_SIZE_REQUIRED);
                }
                if (!StringUtils.hasText(jobCompanyView.getAddress())) {
                    addError(errors, MessageProperties.CompanyField.ADDRESS, MessageProperties.Posting.COMPANY_ADDRESS_REQUIRED);
                }
                if (!StringUtils.hasText(jobCompanyView.getUrl())) {
                    addError(errors, MessageProperties.CompanyField.URL, MessageProperties.Posting.COMPANY_URL_REQUIRED);
                }
            }
        }
    }

    private void addError(Map<String, List<String>> errors, String field, String messageKey) {
        errors.computeIfAbsent(field, k -> new ArrayList<>()).add(getMessage(messageKey));
    }

    private String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, Locale.getDefault());
    }
}
