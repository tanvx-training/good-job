package com.goodjob.job.domain.job.repository.projection;

import lombok.Data;

import java.util.List;

@Data
public class JobSummaryImpl implements JobSummary {

    private Long jobId;
    private Integer companyId;
    private String title;
    private String description;
    private Integer workType;
    private Integer educationLevel;
    private Integer experienceLevel;
    private Boolean remoteAllowed;
    private String location;
    private String zipCode;
    private String skillsDesc;
    private Long expiry;
    private Long closedTime;
    private Integer jobStatus;
    private Integer views;
    private Integer applies;
    private JobSalarySummary jobSalary;
    private List<JobBenefitSummary> jobBenefits;
    private List<JobSkillSummary> jobSkills;
    private List<JobIndustrySummary> jobIndustries;
}
