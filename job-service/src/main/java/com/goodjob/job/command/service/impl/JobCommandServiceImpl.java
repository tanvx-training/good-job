package com.goodjob.job.command.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodjob.job.command.service.JobCommandService;
import com.goodjob.job.entity.*;
import com.goodjob.job.exception.JobNotFoundException;
import com.goodjob.job.mapper.JobMapper;
import com.goodjob.job.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of the JobCommandService interface.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class JobCommandServiceImpl implements JobCommandService {

    private final JobRepository jobRepository;
    private final JobMapper jobMapper;
    private final ObjectMapper objectMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer createJob(CreateJobCommand command, String employerId) {
        log.info("Creating job for employer: {}", employerId);
        
        Job job = jobMapper.toEntity(command, employerId);
        
        // Set salary
        if (command.getSalary() != null) {
            JobSalary salary = jobMapper.toEntity(command.getSalary());
            job.setSalary(salary);
        }
        
        // Set skills
        if (command.getSkills() != null && !command.getSkills().isEmpty()) {
            Set<JobSkill> skills = command.getSkills().stream()
                    .map(jobMapper::toEntity)
                    .collect(Collectors.toSet());
            skills.forEach(job::addSkill);
        }
        
        // Set industries
        if (command.getIndustries() != null && !command.getIndustries().isEmpty()) {
            Set<JobIndustry> industries = command.getIndustries().stream()
                    .map(jobMapper::toEntity)
                    .collect(Collectors.toSet());
            industries.forEach(job::addIndustry);
        }
        
        // Create search vector
        updateSearchVector(job);
        
        Job savedJob = jobRepository.save(job);
        log.info("Job created with ID: {}", savedJob.getId());
        
        return savedJob.getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer updateJob(Integer id, UpdateJobCommand command, String employerId) {
        log.info("Updating job with ID: {} for employer: {}", id, employerId);
        
        Job job = jobRepository.findByIdAndEmployerId(id, employerId)
                .orElseThrow(() -> {
                    log.error("Job not found with ID: {} for employer: {}", id, employerId);
                    return new JobNotFoundException(id);
                });
        
        jobMapper.updateEntityFromCommand(command, job);
        
        // Update salary if provided
        if (command.getSalary() != null) {
            if (job.getSalary() != null) {
                // Update existing salary
                job.getSalary().setMinSalary(command.getSalary().getMinSalary());
                job.getSalary().setMaxSalary(command.getSalary().getMaxSalary());
                job.getSalary().setCurrency(command.getSalary().getCurrency());
                job.getSalary().setPeriod(command.getSalary().getPeriod());
            } else {
                // Create new salary
                JobSalary salary = jobMapper.toEntity(command.getSalary());
                job.setSalary(salary);
            }
        }
        
        // Update skills if provided
        if (command.getSkills() != null && !command.getSkills().isEmpty()) {
            // Remove existing skills
            job.getSkills().clear();
            
            // Add new skills
            Set<JobSkill> skills = command.getSkills().stream()
                    .map(jobMapper::toEntity)
                    .collect(Collectors.toSet());
            skills.forEach(job::addSkill);
        }
        
        // Update industries if provided
        if (command.getIndustries() != null && !command.getIndustries().isEmpty()) {
            // Remove existing industries
            job.getIndustries().clear();
            
            // Add new industries
            Set<JobIndustry> industries = command.getIndustries().stream()
                    .map(jobMapper::toEntity)
                    .collect(Collectors.toSet());
            industries.forEach(job::addIndustry);
        }
        
        // Update search vector
        updateSearchVector(job);
        
        Job updatedJob = jobRepository.save(job);
        log.info("Job updated with ID: {}", updatedJob.getId());
        
        return updatedJob.getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteJob(Integer id, String employerId) {
        log.info("Deleting job with ID: {} for employer: {}", id, employerId);
        
        Job job = jobRepository.findByIdAndEmployerId(id, employerId)
                .orElseThrow(() -> {
                    log.error("Job not found with ID: {} for employer: {}", id, employerId);
                    return new JobNotFoundException(id);
                });
        
        jobRepository.delete(job);
        log.info("Job deleted with ID: {}", id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer updateJobStatus(Integer id, JobStatus status, String employerId) {
        log.info("Updating status of job with ID: {} to {} for employer: {}", id, status, employerId);
        
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Job not found with ID: {}", id);
                    return new JobNotFoundException(id);
                });
        
        // Check if the employer is authorized to update the job
        if (!job.getEmployerId().equals(employerId)) {
            log.error("Employer {} is not authorized to update job with ID: {}", employerId, id);
            throw new UnauthorizedAccessException("You are not authorized to update this job");
        }
        
        job.setStatus(status);
        Job updatedJob = jobRepository.save(job);
        log.info("Job status updated with ID: {}", updatedJob.getId());
        
        return updatedJob.getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer incrementJobViews(Integer id) {
        log.info("Incrementing views for job with ID: {}", id);
        
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Job not found with ID: {}", id);
                    return new JobNotFoundException(id);
                });
        
        if (job.getViews() == null) {
            job.setViews(1);
        } else {
            job.setViews(job.getViews() + 1);
        }
        
        Job updatedJob = jobRepository.save(job);
        log.info("Job views incremented to {} for job with ID: {}", updatedJob.getViews(), updatedJob.getId());
        
        return updatedJob.getViews();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer incrementJobApplications(Integer id) {
        log.info("Incrementing applications for job with ID: {}", id);
        
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Job not found with ID: {}", id);
                    return new JobNotFoundException(id);
                });
        
        if (job.getApplications() == null) {
            job.setApplications(1);
        } else {
            job.setApplications(job.getApplications() + 1);
        }
        
        Job updatedJob = jobRepository.save(job);
        log.info("Job applications incremented to {} for job with ID: {}", updatedJob.getApplications(), updatedJob.getId());
        
        return updatedJob.getApplications();
    }
    
    /**
     * Update the search vector for a job.
     * This creates a JSONB object with searchable fields for fast querying.
     *
     * @param job the job to update
     */
    private void updateSearchVector(Job job) {
        try {
            Map<String, Object> searchVector = new HashMap<>();
            searchVector.put("title", job.getTitle().toLowerCase());
            searchVector.put("company", job.getCompanyName().toLowerCase());
            searchVector.put("location", job.getLocation().toLowerCase());
            
            if (job.getDescription() != null) {
                searchVector.put("description", job.getDescription().toLowerCase());
            }
            
            if (job.getRequirements() != null) {
                searchVector.put("requirements", job.getRequirements().toLowerCase());
            }
            
            if (job.getJobType() != null) {
                searchVector.put("jobType", job.getJobType().name().toLowerCase());
            }
            
            if (job.getExperienceLevel() != null) {
                searchVector.put("experienceLevel", job.getExperienceLevel().name().toLowerCase());
            }
            
            if (job.getEducationLevel() != null) {
                searchVector.put("educationLevel", job.getEducationLevel().name().toLowerCase());
            }
            
            // Add skill IDs
            if (job.getSkills() != null && !job.getSkills().isEmpty()) {
                Set<Integer> skillIds = job.getSkills().stream()
                        .map(skill -> skill.getId().getSkillId())
                        .collect(Collectors.toSet());
                searchVector.put("skills", skillIds);
            }
            
            // Add industry IDs
            if (job.getIndustries() != null && !job.getIndustries().isEmpty()) {
                Set<Integer> industryIds = job.getIndustries().stream()
                        .map(industry -> industry.getId().getIndustryId())
                        .collect(Collectors.toSet());
                searchVector.put("industries", industryIds);
            }
            
            job.setSearchVector(objectMapper.writeValueAsString(searchVector));
        } catch (JsonProcessingException e) {
            log.error("Error creating search vector for job: {}", e.getMessage());
        }
    }
} 