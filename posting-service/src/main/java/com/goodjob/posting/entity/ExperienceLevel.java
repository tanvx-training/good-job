package com.goodjob.posting.entity;

/**
 * Enum representing different levels of experience required for a job.
 */
public enum ExperienceLevel {
    ENTRY_LEVEL("Entry Level"),
    JUNIOR("Junior"),
    MID_LEVEL("Mid Level"),
    SENIOR("Senior"),
    LEAD("Lead"),
    MANAGER("Manager"),
    EXECUTIVE("Executive");

    private final String displayName;

    ExperienceLevel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 