package com.goodjob.job.infrastructure.common.enums;

import com.goodjob.common.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum representing different statuses of a job posting.
 */
@Getter
@RequiredArgsConstructor
public enum PostingStatus implements BaseEnum<PostingStatus> {
    DRAFT(1, "Draft"),
    ACTIVE(2, "Active"),
    PAUSED(3, "Paused"),
    EXPIRED(4, "Expired"),
    FILLED(5, "Filled"),
    CANCELLED(6, "Cancelled");

    private final Integer code;
    private final String displayName;
} 