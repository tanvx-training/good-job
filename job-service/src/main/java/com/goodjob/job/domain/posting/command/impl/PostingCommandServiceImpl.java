package com.goodjob.job.domain.posting.command.impl;

import com.goodjob.common.enums.BaseEnum;
import com.goodjob.job.application.MessageProperties;
import com.goodjob.job.domain.job.entity.Job;
import com.goodjob.job.domain.job.repository.JobRepository;
import com.goodjob.job.domain.posting.command.PostingCommandService;
import com.goodjob.job.domain.posting.dto.CreatePostingCommand;
import com.goodjob.job.infrastructure.common.enums.EducationLevel;
import com.goodjob.job.infrastructure.common.enums.ExperienceLevel;
import com.goodjob.job.infrastructure.common.enums.WorkType;
import com.goodjob.job.infrastructure.feign.CompanyFeignClient;
import com.goodjob.job.infrastructure.feign.MetadataFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PostingCommandServiceImpl implements PostingCommandService {

    private static final Integer COMMON_LENGTH = 255;

    private final MessageSource messageSource;

    private final JobRepository jobRepository;

    private final MetadataFeignClient metadataFeignClient;

    private final CompanyFeignClient companyFeignClient;

    @Override
    public Long createPostingJob(CreatePostingCommand command) {
        List<String> messages = new LinkedList<>();
        Optional<Job> jobOptional = jobRepository.findById(command.getJobId());
        if (jobOptional.isEmpty()) {
            messages.add(getMessage(MessageProperties.Posting.JOB_ID_INVALID,  command.getJobId()));
        } else {
            Job job = jobOptional.get();
            // title
            if (!StringUtils.hasText(job.getTitle())) {
                messages.add(getMessage(MessageProperties.Posting.TITLE_REQUIRED));
            } else {
                if (job.getTitle().trim().length() > COMMON_LENGTH) {
                    messages.add(getMessage(MessageProperties.Posting.TITLE_TOO_LONG, COMMON_LENGTH));
                }
            }
            // description
            if (!StringUtils.hasText(job.getDescription())) {
                messages.add(getMessage(MessageProperties.Posting.DESCRIPTION_REQUIRED));
            }
            // work type
            WorkType workType = BaseEnum.fromValueNullable(WorkType.class ,job.getWorkType());
            if (Objects.isNull(workType)) {
                messages.add(getMessage(MessageProperties.Posting.WORK_TYPE_INVALID, job.getWorkType()));
            }
            // education level
            EducationLevel educationLevel = BaseEnum.fromValueNullable(EducationLevel.class ,job.getEducationLevel());
            if (Objects.isNull(educationLevel)) {
                messages.add(getMessage(MessageProperties.Posting.EDUCATION_LEVEL_INVALID, job.getEducationLevel()));
            }
            // experience level
            ExperienceLevel experienceLevel = BaseEnum.fromValueNullable(ExperienceLevel.class ,job.getExperienceLevel());
            if (Objects.isNull(experienceLevel)) {
                messages.add(getMessage(MessageProperties.Posting.EXPERIENCE_LEVEL_INVALID, job.getExperienceLevel()));
            }
            // remote and location
            if (!Boolean.TRUE.equals(job.getRemoteAllowed()) && !StringUtils.hasText(job.getLocation())) {
                messages.add(getMessage(MessageProperties.Posting.LOCATION_REQUIRED));
            }
            // zip code
            if (!StringUtils.hasText(job.getZipCode())) {
                messages.add(getMessage(MessageProperties.Posting.ZIP_CODE_INVALID, job.getZipCode()));
            }
            // skill describe
            if (!StringUtils.hasText(job.getSkillsDesc())) {
                messages.add(getMessage(MessageProperties.Posting.SKILL_DESC_REQUIRED));
            }
        }

        if (!CollectionUtils.isEmpty(messages)) {
        }
        return 0L;
    }

    private String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, Locale.getDefault());
    }
}
