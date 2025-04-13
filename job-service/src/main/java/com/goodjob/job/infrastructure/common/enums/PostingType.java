package com.goodjob.job.infrastructure.common.enums;

import com.goodjob.common.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostingType implements BaseEnum<PostingType> {

  POSTING(1, "POSTING"),
  UN_POSTING(2, "UN_POSTING");

  private final Integer code;

  private final String description;

  public static PostingType fromValue(Integer code) {
    return BaseEnum.fromValue(PostingType.class, code);
  }
}
