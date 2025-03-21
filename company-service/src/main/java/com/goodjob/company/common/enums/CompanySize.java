package com.goodjob.company.common.enums;

import com.goodjob.common.exception.ResourceNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Objects;

/**
 * Enum representing different company sizes.
 */
@Slf4j
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

    private final Integer code;
    private final String description;

    public static CompanySize fromValue(Integer code) {
        log.info("CompanySize fromValue:{}", code);
        return Arrays.stream(CompanySize.values())
                .filter(e -> Objects.equals(e.code, code))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        CompanySize.class.getName(),
                        "code",
                        code
                ));
    }
} 