package com.goodjob.company.query.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyQuery {

    private Integer page;
    private Integer size;
    private String sort;
}
