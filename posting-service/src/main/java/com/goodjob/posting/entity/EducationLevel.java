package com.goodjob.posting.entity;

/**
 * Enum representing different levels of education required for a job.
 */
public enum EducationLevel {
    HIGH_SCHOOL("High School"),
    ASSOCIATE("Associate's Degree"),
    BACHELOR("Bachelor's Degree"),
    MASTER("Master's Degree"),
    DOCTORATE("Doctorate"),
    PROFESSIONAL("Professional Degree"),
    CERTIFICATION("Certification"),
    NO_REQUIREMENT("No Requirement");

    private final String displayName;

    EducationLevel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 