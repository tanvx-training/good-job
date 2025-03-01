package com.goodjob.company.model;

/**
 * Enum representing different company sizes.
 */
public enum CompanySize {
    STARTUP("1-10 employees"),
    SMALL("11-50 employees"),
    MEDIUM("51-200 employees"),
    LARGE("201-1000 employees"),
    ENTERPRISE("1000+ employees");

    private final String displayName;

    CompanySize(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 