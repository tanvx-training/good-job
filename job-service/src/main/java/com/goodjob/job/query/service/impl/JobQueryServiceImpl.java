package com.goodjob.job.query.service.impl;

import com.goodjob.common.dto.PageResponseDTO;
import com.goodjob.common.util.DateTimeUtils;
import com.goodjob.job.common.enums.EducationLevel;
import com.goodjob.job.common.enums.ExperienceLevel;
import com.goodjob.job.common.enums.JobStatus;
import com.goodjob.job.common.enums.WorkType;
import com.goodjob.job.query.dto.JobQuery;
import com.goodjob.job.query.dto.JobView;
import com.goodjob.job.query.service.JobQueryService;
import com.goodjob.job.repository.JobRepository;
import com.goodjob.job.repository.JobSummary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the JobQueryService interface.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class JobQueryServiceImpl implements JobQueryService {

    private final JobRepository jobRepository;

    @Override
    public PageResponseDTO<JobView> getAllJobs(JobQuery query) {

        String[] parts = query.getSort().split(",");
        Sort sort = Sort.by(Sort.Direction.fromString(parts[1]), parts[0]);
        Pageable pageable = PageRequest.of(query.getPage(), query.getSize(), sort);

        Page<JobView> jobViewPage = jobRepository.findByDeleteFlg(false, pageable)
                .map(this::convertFromSummaryToView);

        return new PageResponseDTO<>(jobViewPage);
    }

    private JobView convertFromSummaryToView(JobSummary summary) {
        return JobView.builder()
            .jobId(summary.getJobId())
            .company(null)
            .title(summary.getTitle())
            .description(summary.getDescription())
            .workType(WorkType.fromValue(summary.getWorkType()).getDescription())
            .educationLevel(EducationLevel.fromValue(summary.getEducationLevel()).getDescription())
            .experienceLevel(ExperienceLevel.fromValue(summary.getExperienceLevel()).getDescription())
            .remoteAllowed(summary.getRemoteAllowed())
            .location(summary.getLocation())
            .zipCode(summary.getZipCode())
            .skillsDesc(summary.getSkillsDesc())
            .expiry(DateTimeUtils.fromTimestamp(summary.getExpiry()))
            .closedTime(DateTimeUtils.fromTimestamp(summary.getClosedTime()))
            .jobStatus(JobStatus.fromValue(summary.getJobStatus()).getDescription())
            .benefits(null)
            .skills(null)
            .industries(null)
            .build();
    }
}