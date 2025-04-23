package com.goodjob.job.infrastructure.common.enums;

import com.goodjob.common.application.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExperienceLevel implements BaseEnum<ExperienceLevel> {
  INTERN(1, "Internship"),
  ENTRY_LEVEL(2, "Entry Level"),
  ASSOCIATE(3, "Associate"),
  MID_SENIOR_LEVEL(4, "Mid-Senior Level"),
  DIRECTOR(5, "Director"),
  EXECUTIVE(6, "Executive"),
  NOT_SPECIFIED(7, "Not Specified");

  private final Integer code;
  private final String description;

  public static ExperienceLevel fromValue(Integer code) {
    return BaseEnum.fromValue(ExperienceLevel.class, code);
  }
}

