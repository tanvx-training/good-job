package com.goodjob.job.domain.job.repository.projection;

import java.util.List;

public interface JobSummary {

    Long getJobId();

    Integer getCompanyId();

    String getTitle();

    String getDescription();

    Integer getWorkType();

    Integer getEducationLevel();

    Integer getExperienceLevel();

    Boolean getRemoteAllowed();

    String getLocation();

    String getZipCode();

    String getSkillsDesc();

    Long getExpiry();

    Long getClosedTime();

    Integer getJobStatus();

    Integer getViews();

    Integer getApplies();

    JobSalarySummary getJobSalary();

    List<JobBenefitSummary> getJobBenefits();

    List<JobSkillSummary> getJobSkills();

    List<JobIndustrySummary> getJobIndustries();
}
