package com.goodjob.metadata.query.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class SpecialityQuery {
    private final Integer page;
    private final Integer size;
    private final String sort;
}
