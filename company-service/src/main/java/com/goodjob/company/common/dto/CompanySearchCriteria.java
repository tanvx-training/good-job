package com.goodjob.company.common.dto;

import java.util.Set;

/**
 * Data Transfer Object for company search criteria.
 * Contains various parameters that can be used to filter company search results.
 */
public class CompanySearchCriteria {
    private String keyword;
    private String location;
    private String industry;
    private String companySize;
    private Set<String> specialties;
    private Boolean verified;

    public CompanySearchCriteria() {
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

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public Set<String> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(Set<String> specialties) {
        this.specialties = specialties;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    @Override
    public String toString() {
        return "CompanySearchCriteria{" +
                "keyword='" + keyword + '\'' +
                ", location='" + location + '\'' +
                ", industry='" + industry + '\'' +
                ", companySize='" + companySize + '\'' +
                ", specialties=" + specialties +
                ", verified=" + verified +
                '}';
    }
} 