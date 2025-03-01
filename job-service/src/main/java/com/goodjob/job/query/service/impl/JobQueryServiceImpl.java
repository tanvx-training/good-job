package com.goodjob.job.query.service.impl;

import com.goodjob.job.entity.Job;
import com.goodjob.job.entity.JobStatus;
import com.goodjob.job.exception.JobNotFoundException;
import com.goodjob.job.mapper.JobMapper;
import com.goodjob.job.query.dto.JobSearchCriteria;
import com.goodjob.job.query.dto.JobView;
import com.goodjob.job.query.service.JobQueryService;
import com.goodjob.job.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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
    private final JobMapper jobMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public JobView getJobById(Integer id) {
        log.info("Retrieving job with ID: {}", id);
        
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Job not found with ID: {}", id);
                    return new JobNotFoundException(id);
                });
        
        return jobMapper.toView(job);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<JobView> getJobsByEmployerId(String employerId, int page, int size, String sort, String direction) {
        log.info("Retrieving jobs for employer: {}", employerId);
        
        Pageable pageable = createPageable(page, size, sort, direction);
        Page<Job> jobs = jobRepository.findByEmployerId(employerId, pageable);
        
        return jobs.map(jobMapper::toView);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<JobView> getJobsByStatus(JobStatus status, int page, int size, String sort, String direction) {
        log.info("Retrieving jobs with status: {}", status);
        
        Pageable pageable = createPageable(page, size, sort, direction);
        Page<Job> jobs = jobRepository.findByStatus(status, pageable);
        
        return jobs.map(jobMapper::toView);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<JobView> getJobsByEmployerIdAndStatus(String employerId, JobStatus status, int page, int size, String sort, String direction) {
        log.info("Retrieving jobs for employer: {} with status: {}", employerId, status);
        
        Pageable pageable = createPageable(page, size, sort, direction);
        Page<Job> jobs = jobRepository.findByEmployerIdAndStatus(employerId, status, pageable);
        
        return jobs.map(jobMapper::toView);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<JobView> searchJobs(String query, int page, int size, String sort, String direction) {
        log.info("Searching jobs with query: {}", query);
        
        Pageable pageable = createPageable(page, size, sort, direction);
        Page<Job> jobs = jobRepository.searchJobs(query.toLowerCase(), pageable);
        
        return jobs.map(jobMapper::toView);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<JobView> searchJobsWithCriteria(JobSearchCriteria criteria, int page, int size, String sort, String direction) {
        log.info("Searching jobs with criteria: {}", criteria);
        
        Pageable pageable = createPageable(page, size, sort, direction);
        
        Specification<Job> spec = Specification.where(null);
        
        // Add filters based on criteria
        if (criteria.getKeyword() != null && !criteria.getKeyword().isEmpty()) {
            spec = spec.and((root, query, cb) -> 
                cb.or(
                    cb.like(cb.lower(root.get("title")), "%" + criteria.getKeyword().toLowerCase() + "%"),
                    cb.like(cb.lower(root.get("description")), "%" + criteria.getKeyword().toLowerCase() + "%"),
                    cb.like(cb.lower(root.get("companyName")), "%" + criteria.getKeyword().toLowerCase() + "%"),
                    cb.like(cb.lower(root.get("location")), "%" + criteria.getKeyword().toLowerCase() + "%")
                )
            );
        }
        
        if (criteria.getLocation() != null && !criteria.getLocation().isEmpty()) {
            spec = spec.and((root, query, cb) -> 
                cb.like(cb.lower(root.get("location")), "%" + criteria.getLocation().toLowerCase() + "%")
            );
        }
        
        if (criteria.getCompanyName() != null && !criteria.getCompanyName().isEmpty()) {
            spec = spec.and((root, query, cb) -> 
                cb.like(cb.lower(root.get("companyName")), "%" + criteria.getCompanyName().toLowerCase() + "%")
            );
        }
        
        if (criteria.getJobType() != null) {
            spec = spec.and((root, query, cb) -> 
                cb.equal(root.get("jobType"), criteria.getJobType())
            );
        }
        
        if (criteria.getExperienceLevel() != null) {
            spec = spec.and((root, query, cb) -> 
                cb.equal(root.get("experienceLevel"), criteria.getExperienceLevel())
            );
        }
        
        if (criteria.getEducationLevel() != null) {
            spec = spec.and((root, query, cb) -> 
                cb.equal(root.get("educationLevel"), criteria.getEducationLevel())
            );
        }
        
        // Only include active jobs
        spec = spec.and((root, query, cb) -> 
            cb.equal(root.get("status"), JobStatus.ACTIVE)
        );
        
        // Only include jobs that haven't expired
        spec = spec.and((root, query, cb) -> 
            cb.or(
                cb.isNull(root.get("expiresAt")),
                cb.greaterThan(root.get("expiresAt"), LocalDateTime.now())
            )
        );
        
        Page<Job> jobs = jobRepository.findAll(spec, pageable);
        
        return jobs.map(jobMapper::toView);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<JobView> getExpiredJobs() {
        log.info("Retrieving expired jobs");
        
        List<Job> expiredJobs = jobRepository.findByExpiresAtBefore(LocalDateTime.now());
        
        return expiredJobs.stream()
                .map(jobMapper::toView)
                .collect(Collectors.toList());
    }

    /**
     * Create a Pageable object for pagination and sorting.
     *
     * @param page      the page number (0-based)
     * @param size      the page size
     * @param sort      the field to sort by
     * @param direction the sort direction (ASC or DESC)
     * @return a Pageable object
     */
    private Pageable createPageable(int page, int size, String sort, String direction) {
        Sort.Direction sortDirection = Sort.Direction.ASC;
        
        if (direction != null && direction.equalsIgnoreCase("desc")) {
            sortDirection = Sort.Direction.DESC;
        }
        
        if (sort == null || sort.isEmpty()) {
            sort = "createdAt";
            sortDirection = Sort.Direction.DESC;
        }
        
        return PageRequest.of(page, size, Sort.by(sortDirection, sort));
    }
} 