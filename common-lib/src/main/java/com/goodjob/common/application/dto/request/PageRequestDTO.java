package com.goodjob.common.application.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.io.Serial;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * Data Transfer Object for pagination requests.
 * Used to standardize pagination across all services.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_SIZE = 100;

    @Min(value = 0, message = "Page number cannot be less than 0")
    @Builder.Default
    private Integer page = DEFAULT_PAGE;

    @Min(value = 1, message = "Page size cannot be less than 1")
    @Max(value = MAX_SIZE, message = "Page size cannot be greater than " + MAX_SIZE)
    @Builder.Default
    private Integer size = DEFAULT_SIZE;

    private String sortBy;
    private String sortDirection;

    /**
     * Converts this DTO to a Spring Pageable object.
     *
     * @return a Pageable object configured with the pagination parameters
     */
    public Pageable toPageable() {
        Sort sort = Sort.unsorted();
        
        if (sortBy != null && !sortBy.isEmpty()) {
            sort = Sort.by(sortBy);
            
            if (sortDirection != null && sortDirection.equalsIgnoreCase("desc")) {
                sort = sort.descending();
            } else {
                sort = sort.ascending();
            }
        }
        
        return PageRequest.of(page, size, sort);
    }
} 