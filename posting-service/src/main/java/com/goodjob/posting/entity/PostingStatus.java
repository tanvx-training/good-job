package com.goodjob.posting.entity;

/**
 * Enum representing different statuses of a job posting.
 */
public enum PostingStatus {
    DRAFT("Draft"),
    ACTIVE("Active"),
    PAUSED("Paused"),
    EXPIRED("Expired"),
    FILLED("Filled"),
    CANCELLED("Cancelled");

    private final String displayName;

    PostingStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 