package com.goodjob.job.repository;

import java.util.List;

public interface JobSummary {

  Long getJobId();

  Integer getCompanyId();

  String getTitle();

  String getDescription();

  Integer getWorkType();

  Integer getEducationLevel();

  Integer getExperienceLevel();

  boolean getRemoteAllowed();

  String getLocation();
  String getZipCode();
  String getSkillsDesc();
  Long getExpiry();
  Long getClosedTime();
  Integer getJobStatus();
  JobSalarySummary getJobSalary();
  List<JobBenefitSummary> getJobBenefits();
  List<JobSkillSummary> getJobSkills();
  List<JobIndustrySummary> getJobIndustries();
}
