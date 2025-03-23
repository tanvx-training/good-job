package com.goodjob.company.common.enums;

import com.goodjob.common.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Enum representing different company sizes.
 */
@Slf4j
@Getter
@RequiredArgsConstructor
public enum CompanySize implements BaseEnum<CompanySize> {
    SOLO(1, "1 employee"),
    MICRO(2, "2-9 employees"),
    SMALL(3, "10-49 employees"),
    MEDIUM(4, "50-249 employees"),
    LARGE(5, "250-999 employees"),
    VERY_LARGE(6, "1000-4999 employees"),
    ENTERPRISE(7, "5000+ employees");

    private final Integer code;
    private final String description;

    public static CompanySize fromValue(Integer code) {
        return BaseEnum.fromValue(CompanySize.class, code);
    }
} 