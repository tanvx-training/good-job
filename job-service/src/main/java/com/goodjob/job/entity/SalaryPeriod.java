package com.goodjob.job.entity;

/**
 * Enum representing different periods for salary payment.
 */
public enum SalaryPeriod {
    HOURLY("Hourly"),
    DAILY("Daily"),
    WEEKLY("Weekly"),
    BIWEEKLY("Bi-weekly"),
    MONTHLY("Monthly"),
    ANNUAL("Annual");

    private final String displayName;

    SalaryPeriod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 