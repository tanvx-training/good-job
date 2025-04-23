package com.goodjob.job.infrastructure.common.enums;

import com.goodjob.common.application.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CompensationType implements BaseEnum<CompensationType> {
  SALARY(1, "Salary"),
  HOURLY(2, "Hourly"),
  COMMISSION(3, "Commission"),
  BONUS(4, "Bonus"),
  EQUITY(5, "Equity"),
  PROFIT_SHARING(6, "Profit Sharing"),
  OTHER(7, "Other");

  private final Integer code;
  private final String description;

  public static CompensationType fromValue(Integer code) {
    return BaseEnum.fromValue(CompensationType.class, code);
  }
}
