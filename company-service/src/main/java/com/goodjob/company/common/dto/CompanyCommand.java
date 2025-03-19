package com.goodjob.company.common.dto;

import jakarta.validation.constraints.*;
import java.util.Set;

/**
 * Data Transfer Object for company creation and update operations.
 */
public class CompanyCommand {
    
    @NotBlank(message = "Company name is required")
    @Size(min = 2, max = 100, message = "Company name must be between 2 and 100 characters")
    private String name;
    
    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 5000, message = "Description must be between 10 and 5000 characters")
    private String description;
    
    @Pattern(regexp = "^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w .-]*)*/?$", message = "Website must be a valid URL")
    private String website;
    
    @NotBlank(message = "Industry is required")
    private String industry;
    
    @NotBlank(message = "Company size is required")
    private String companySize;
    
    @Min(value = 1800, message = "Founded year must be valid")
    @Max(value = 2100, message = "Founded year must be valid")
    private Integer foundedYear;
    
    @NotBlank(message = "Headquarters is required")
    @Size(max = 100, message = "Headquarters must not exceed 100 characters")
    private String headquarters;
    
    private String logoUrl;
    
    private String bannerUrl;
    
    private Set<String> specialties;

    public CompanyCommand() {
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
} 