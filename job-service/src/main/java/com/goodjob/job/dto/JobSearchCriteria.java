package com.goodjob.job.dto;

import java.util.List;

/**
 * Data Transfer Object for job search criteria.
 * Contains various parameters that can be used to filter job search results.
 */
public class JobSearchCriteria {
    private String keyword;
    private String location;
    private String companyName;
    private List<String> jobTypes;
    private List<String> experienceLevels;
    private List<String> educationLevels;
    private List<String> skills;
    private List<String> industries;
    private Double minSalary;
    private Double maxSalary;
    private Boolean remote;

    public JobSearchCriteria() {
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<String> getJobTypes() {
        return jobTypes;
    }

    public void setJobTypes(List<String> jobTypes) {
        this.jobTypes = jobTypes;
    }

    public List<String> getExperienceLevels() {
        return experienceLevels;
    }

    public void setExperienceLevels(List<String> experienceLevels) {
        this.experienceLevels = experienceLevels;
    }

    public List<String> getEducationLevels() {
        return educationLevels;
    }

    public void setEducationLevels(List<String> educationLevels) {
        this.educationLevels = educationLevels;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getIndustries() {
        return industries;
    }

    public void setIndustries(List<String> industries) {
        this.industries = industries;
    }

    public Double getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(Double minSalary) {
        this.minSalary = minSalary;
    }

    public Double getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(Double maxSalary) {
        this.maxSalary = maxSalary;
    }

    public Boolean getRemote() {
        return remote;
    }

    public void setRemote(Boolean remote) {
        this.remote = remote;
    }
} 