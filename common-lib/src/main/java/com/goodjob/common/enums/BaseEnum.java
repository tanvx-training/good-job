package com.goodjob.common.enums;

import com.goodjob.common.exception.ResourceNotFoundException;
import java.util.Arrays;

public interface BaseEnum<T extends Enum<T>> {

  Integer getCode();

  static <T extends Enum<T> & BaseEnum<T>> T fromValue(Class<T> enumClass, Integer code) {
    return Arrays.stream(enumClass.getEnumConstants())
        .filter(e -> e.getCode().equals(code))
        .findFirst()
        .orElseThrow(() -> new ResourceNotFoundException(
            enumClass.getSimpleName(),
            "code",
            code
        ));
  }
}
