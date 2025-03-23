package com.goodjob.job.common.enums;

import com.goodjob.common.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WorkType implements BaseEnum<WorkType> {
    FULL_TIME(1, "Full Time"),
    PART_TIME(2, "Part Time"),
    CONTRACT(3, "Contract"),
    TEMPORARY(4, "Temporary"),
    INTERNSHIP(5, "Internship"),
    FREELANCE(6, "Freelance");

    private final Integer code;

    private final String description;

    public static WorkType fromValue(Integer code) {
        return BaseEnum.fromValue(WorkType.class, code);
    }
}
