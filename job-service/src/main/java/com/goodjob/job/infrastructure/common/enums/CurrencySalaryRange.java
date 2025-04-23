package com.goodjob.job.infrastructure.common.enums;

import com.goodjob.common.application.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CurrencySalaryRange implements BaseEnum<CurrencySalaryRange> {

    USD(1, "USD", new BigDecimal("1000"), new BigDecimal("500000")),
    EUR(2, "EUR", new BigDecimal("900"), new BigDecimal("400000")),
    GBP(3, "GBP", new BigDecimal("800"), new BigDecimal("300000")),
    JPY(4, "JPY", new BigDecimal("100000"), new BigDecimal("10000000")),
    VND(5, "VND", new BigDecimal("3000000"), new BigDecimal("200000000")),
    AUD(6, "AUD", new BigDecimal("1200"), new BigDecimal("450000")),
    CAD(7, "CAD", new BigDecimal("1100"), new BigDecimal("420000")),
    CHF(8, "CHF", new BigDecimal("1000"), new BigDecimal("400000")),
    CNY(9, "CNY", new BigDecimal("7000"), new BigDecimal("900000")),
    INR(10, "INR", new BigDecimal("50000"), new BigDecimal("5000000"));

    private final Integer code;
    private final String symbol;
    private final BigDecimal min;
    private final BigDecimal max;

    public static CurrencySalaryRange fromValue(String symbol) {
        return Arrays.stream(CurrencySalaryRange.class.getEnumConstants())
                .filter(e -> e.getSymbol().equals(symbol))
                .findFirst()
                .orElse(null);
    }
}
