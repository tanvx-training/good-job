package com.goodjob.profile.domain.profile.dto;

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
public class ProfileSearchQuery {
    
    private Integer page;
    private Integer size;
    private String sort;
    private String keyword;
} 