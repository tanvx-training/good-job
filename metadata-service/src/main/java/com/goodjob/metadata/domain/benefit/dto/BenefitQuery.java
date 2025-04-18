package com.goodjob.metadata.domain.benefit.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class BenefitQuery {
  private final Integer page;
  private final Integer size;
  private final String sort;
}
