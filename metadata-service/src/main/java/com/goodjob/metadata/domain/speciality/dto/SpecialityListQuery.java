package com.goodjob.metadata.domain.speciality.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class SpecialityListQuery {

    private List<Integer> idList;
}
