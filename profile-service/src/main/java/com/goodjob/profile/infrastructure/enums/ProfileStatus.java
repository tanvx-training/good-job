package com.goodjob.profile.infrastructure.enums;

import com.goodjob.common.application.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProfileStatus implements BaseEnum<ProfileStatus> {
    ACTIVE(1, "ACTIVE"),
    INACTIVE(2, "INACTIVE"),
    LOOKING_FOR_JOB(3, "LOOKING_FOR_JOB");

    private final Integer code;
    private final String description;
    public static ProfileStatus fromValue(Integer code) {
        return BaseEnum.fromValue(ProfileStatus.class, code);
    }
}
