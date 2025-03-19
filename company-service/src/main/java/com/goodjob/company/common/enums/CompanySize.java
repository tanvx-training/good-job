package com.goodjob.company.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum representing different company sizes.
 */
@Getter
@RequiredArgsConstructor
public enum CompanySize {
    SOLO(1, "1 employee"),
    MICRO(2, "2-9 employees"),
    SMALL(3, "10-49 employees"),
    MEDIUM(4, "50-249 employees"),
    LARGE(5, "250-999 employees"),
    VERY_LARGE(6, "1000-4999 employees"),
    ENTERPRISE(7, "5000+ employees");

    private final Integer value;
    private final String description;
} 