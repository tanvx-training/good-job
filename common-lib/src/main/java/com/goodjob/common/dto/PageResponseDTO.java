package com.goodjob.common.dto;

import java.io.Serial;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

/**
 * Data Transfer Object for pagination responses.
 * Used to standardize pagination responses across all services.
 *
 * @param <T> the type of elements in the page
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDTO<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<T> content;
    private long totalElements;
    private int totalPages;
    private int page;
    private int size;
    private boolean first;
    private boolean last;
    private boolean empty;

    /**
     * Constructs a PageResponseDTO from a Spring Page object.
     *
     * @param page the Spring Page object
     */
    public PageResponseDTO(Page<T> page) {
        this.content = page.getContent();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.page = page.getNumber();
        this.size = page.getSize();
        this.first = page.isFirst();
        this.last = page.isLast();
        this.empty = page.isEmpty();
    }
} 