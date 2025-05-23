package com.goodjob.metadata.domain.skill.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class SkillQuery {
  private final Integer page;
  private final Integer size;
  private final String sort;
}
