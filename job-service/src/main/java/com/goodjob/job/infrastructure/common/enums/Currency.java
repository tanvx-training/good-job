package com.goodjob.job.infrastructure.common.enums;

import com.goodjob.common.application.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Currency implements BaseEnum<Currency> {
  USD(1, "United States Dollar", "USD"),
  EUR(2, "Euro", "EUR"),
  GBP(3, "British Pound", "GBP"),
  JPY(4, "Japanese Yen", "JPY"),
  VND(5, "Vietnamese Dong", "VND"),
  AUD(6, "Australian Dollar", "AUD"),
  CAD(7, "Canadian Dollar", "CAD"),
  CHF(8, "Swiss Franc", "CHF"),
  CNY(9, "Chinese Yuan", "CNY"),
  INR(10, "Indian Rupee", "INR");

  private final Integer code;
  private final String description;
  private final String symbol;

  public static Currency fromValue(Integer code) {
    return BaseEnum.fromValue(Currency.class, code);
  }
}
