package com.goodjob.job.model;

/**
 * Enum representing different education levels for job postings.
 */
public enum EducationLevel {
    HIGH_SCHOOL("High School"),
    ASSOCIATE("Associate's Degree"),
    BACHELOR("Bachelor's Degree"),
    MASTER("Master's Degree"),
    DOCTORATE("Doctorate"),
    PROFESSIONAL("Professional Degree"),
    NOT_SPECIFIED("Not Specified");

    private final String displayName;

    EducationLevel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}