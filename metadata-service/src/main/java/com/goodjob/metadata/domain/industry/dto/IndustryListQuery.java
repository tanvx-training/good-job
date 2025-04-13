package com.goodjob.metadata.domain.industry.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class IndustryListQuery {

    private List<Integer> idList;
}
