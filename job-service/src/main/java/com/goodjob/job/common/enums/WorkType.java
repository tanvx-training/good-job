package com.goodjob.job.common.enums;

import com.goodjob.common.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WorkType implements BaseEnum<WorkType> {
  INTERNSHIP(1, "INTERNSHIP"),
  FULL_TIME(2, "FULL_TIME"),
  OTHER(3, "OTHER"),
  PART_TIME(4, "PART_TIME"),
  CONTRACT(5, "CONTRACT"),
  TEMPORARY(6, "TEMPORARY"),
  VOLUNTEER(7, "VOLUNTEER");


  private final Integer code;

  private final String description;

  public static WorkType fromValue(Integer code) {
    return BaseEnum.fromValue(WorkType.class, code);
  }
}
