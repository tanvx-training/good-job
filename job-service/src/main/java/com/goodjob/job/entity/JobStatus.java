package com.goodjob.job.entity;

/**
 * Enum representing different statuses of a job posting.
 */
public enum JobStatus {
    DRAFT("Draft"),
    ACTIVE("Active"),
    PAUSED("Paused"),
    EXPIRED("Expired"),
    FILLED("Filled"),
    CANCELLED("Cancelled");

    private final String displayName;

    JobStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 