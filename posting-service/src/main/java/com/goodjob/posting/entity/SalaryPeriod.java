package com.goodjob.posting.entity;

/**
 * Enum representing different periods for salary payment.
 */
public enum SalaryPeriod {
    HOURLY("Hourly"),
    DAILY("Daily"),
    WEEKLY("Weekly"),
    BIWEEKLY("Bi-weekly"),
    MONTHLY("Monthly"),
    YEARLY("Yearly");

    private final String displayName;

    SalaryPeriod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 