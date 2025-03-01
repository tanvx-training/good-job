package com.goodjob.job.query.dto;

import com.goodjob.job.entity.EducationLevel;
import com.goodjob.job.entity.ExperienceLevel;
import com.goodjob.job.entity.JobStatus;
import com.goodjob.job.entity.JobType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for displaying job information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobView {
    private Integer id;
    private String title;
    private String description;
    private String requirements;
    private String employerId;
    private String companyName;
    private String location;
    private JobType jobType;
    private ExperienceLevel experienceLevel;
    private EducationLevel educationLevel;
    private JobStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime expiresAt;
    private Integer views;
    private Integer applications;
    private JobSalaryView salary;
    private Set<JobSkillView> skills;
    private Set<JobIndustryView> industries;
} 