package com.goodjob.profile.infrastructure.enums;

import com.goodjob.common.application.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProficiencyLevel implements BaseEnum<ProficiencyLevel> {
    BEGINNER(1, "BEGINNER"),
    INTERMEDIATE(2, "INTERMEDIATE"),
    ADVANCED(3, "ADVANCED"),
    EXPERT(4, "EXPERT");

    private final Integer code;

    private final String description;

    public static ProficiencyLevel fromValue(Integer code) {
        return BaseEnum.fromValue(ProficiencyLevel.class, code);
    }
}
