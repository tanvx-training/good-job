package com.goodjob.company.dto;

import com.goodjob.company.entity.Company;
import com.goodjob.company.entity.CompanyMetrics;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Data Transfer Object for company responses in API calls.
 */
public class CompanyResponse {
    private Integer id;
    private String name;
    private String description;
    private String website;
    private String industry;
    private String companySize;
    private Integer foundedYear;
    private String headquarters;
    private String logoUrl;
    private String bannerUrl;
    private Set<String> specialties;
    private Integer employerId;
    private Boolean verified;
    private Integer viewCount;
    private Integer jobCount;
    private Integer followerCount;
    private Double averageRating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CompanyResponse() {
    }

    /**
     * Constructs a CompanyResponse from a Company entity and its metrics.
     *
     * @param company the Company entity
     * @param metrics the CompanyMetrics entity
     * @return a new CompanyResponse
     */
    public static CompanyResponse fromEntity(Company company, CompanyMetrics metrics) {
        CompanyResponse response = new CompanyResponse();
        response.setId(company.getId());
        response.setName(company.getName());
        response.setDescription(company.getDescription());
        response.setWebsite(company.getWebsite());
        response.setIndustry(company.getIndustry());
        response.setCompanySize(company.getCompanySize());
        response.setFoundedYear(company.getFoundedYear());
        response.setHeadquarters(company.getHeadquarters());
        response.setLogoUrl(company.getLogoUrl());
        response.setBannerUrl(company.getBannerUrl());
        response.setSpecialties(company.getSpecialties());
        response.setEmployerId(company.getEmployerId());
        response.setVerified(company.isVerified());
        response.setCreatedAt(company.getCreatedAt());
        response.setUpdatedAt(company.getUpdatedAt());
        
        if (metrics != null) {
            response.setViewCount(metrics.getViewCount());
            response.setJobCount(metrics.getJobCount());
            response.setFollowerCount(metrics.getFollowerCount());
            response.setAverageRating(metrics.getAverageRating());
        }
        
        return response;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

    public Integer getFoundedYear() {
        return foundedYear;
    }

    public void setFoundedYear(Integer foundedYear) {
        this.foundedYear = foundedYear;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public void setHeadquarters(String headquarters) {
        this.headquarters = headquarters;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public Set<String> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(Set<String> specialties) {
        this.specialties = specialties;
    }

    public Integer getEmployerId() {
        return employerId;
    }

    public void setEmployerId(Integer employerId) {
        this.employerId = employerId;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getJobCount() {
        return jobCount;
    }

    public void setJobCount(Integer jobCount) {
        this.jobCount = jobCount;
    }

    public Integer getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(Integer followerCount) {
        this.followerCount = followerCount;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
} 