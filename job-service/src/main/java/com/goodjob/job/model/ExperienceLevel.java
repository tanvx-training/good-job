package com.goodjob.job.model;

/**
 * Enum representing different experience levels for job postings.
 */
public enum ExperienceLevel {
    ENTRY_LEVEL("Entry Level"),
    MID_LEVEL("Mid Level"),
    SENIOR_LEVEL("Senior Level"),
    EXECUTIVE("Executive"),
    NOT_SPECIFIED("Not Specified");

    private final String displayName;

    ExperienceLevel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 