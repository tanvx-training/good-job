package com.goodjob.job.common.enums;

import com.goodjob.common.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PayPeriod implements BaseEnum<PayPeriod> {
  HOURLY(1, "Hourly"),
  DAILY(2, "Daily"),
  WEEKLY(3, "Weekly"),
  BIWEEKLY(4, "Biweekly"),
  MONTHLY(5, "Monthly"),
  ANNUALLY(6, "Annually"),
  ONE_TIME(7, "One-Time");

  private final Integer code;
  private final String description;

  public static PayPeriod fromValue(Integer code) {
    return BaseEnum.fromValue(PayPeriod.class, code);
  }
}
