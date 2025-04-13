package com.goodjob.job.infrastructure.common.enums;

import com.goodjob.common.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JobStatus implements BaseEnum<JobStatus> {
  OPEN(1, "Open"),
  CLOSED(2, "Closed"),
  PENDING(3, "Pending"),
  DRAFT(4, "Draft"),
  CANCELLED(5, "Cancelled"),
  EXPIRED(6, "Expired");

  private final Integer code;
  private final String description;

  public static JobStatus fromValue(Integer code) {
    return BaseEnum.fromValue(JobStatus.class, code);
  }
}
