package com.goodjob.job.domain.job.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class JobQuery implements Serializable {

  private Integer page;
  private Integer size;
  private String sort;
}
