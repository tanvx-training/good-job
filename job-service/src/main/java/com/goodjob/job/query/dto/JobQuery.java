package com.goodjob.job.query.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobQuery {

  private Integer page;
  private Integer size;
  private String sort;
}
