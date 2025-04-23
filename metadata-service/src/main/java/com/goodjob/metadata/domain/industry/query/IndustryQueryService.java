package com.goodjob.metadata.domain.industry.query;

import com.goodjob.common.application.dto.response.PageResponseDTO;
import com.goodjob.metadata.domain.industry.dto.IndustryQuery;
import com.goodjob.metadata.domain.industry.dto.IndustryView;

import java.util.List;

/**
 * Service interface for industry query operations.
 */
public interface IndustryQueryService {

    /**
     * Get all industries.
     *
     * @return list of all industries
     */
    PageResponseDTO<IndustryView> getAllIndustries(IndustryQuery query);

    /**
     * Get an industry by ID.
     *
     * @param id the industry ID
     * @return the industry
     */
    IndustryView getIndustryById(Integer id);

    List<IndustryView> getAllByIdList(List<Integer> idList);
} 