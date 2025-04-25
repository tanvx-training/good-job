package com.goodjob.job.domain.job.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobQuery implements Serializable {

  private Integer page;
  private Integer size;
  private String sort;
}
