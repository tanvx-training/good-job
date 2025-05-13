package com.goodjob.profile.domain.profile.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for profile search query parameters.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProfileSearchQuery {
    
    private Integer page;
    private Integer size;
    private String sort;
    private String keyword;
} 