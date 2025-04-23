package com.goodjob.job.infrastructure.common.enums;

import com.goodjob.common.application.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EducationLevel implements BaseEnum<EducationLevel> {
  HIGH_SCHOOL(1, "High School"),
  ASSOCIATE(2, "Associate Degree"),
  BACHELOR(3, "Bachelor's Degree"),
  MASTER(4, "Master's Degree"),
  DOCTORATE(5, "Doctorate (PhD)"),
  PROFESSIONAL(6, "Professional Certification"),
  OTHER(7, "Other");

  private final Integer code;
  private final String description;

  public static EducationLevel fromValue(Integer code) {
    return BaseEnum.fromValue(EducationLevel.class, code);
  }
}

