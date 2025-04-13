package com.goodjob.metadata.domain.industry.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class IndustryQuery {
    private final Integer page;
    private final Integer size;
    private final String sort;
}
