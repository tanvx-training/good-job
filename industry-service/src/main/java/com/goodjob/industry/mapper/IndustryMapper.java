package com.goodjob.industry.mapper;

import com.goodjob.industry.dto.IndustryDto;
import com.goodjob.industry.entity.Industry;
import com.goodjob.industry.query.dto.IndustryView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for Industry entity and DTOs.
 */
@Mapper(componentModel = "spring")
public interface IndustryMapper {

    /**
     * Convert Industry entity to IndustryDto.
     *
     * @param industry the Industry entity
     * @return the IndustryDto
     */
    IndustryDto toDto(Industry industry);

    /**
     * Convert IndustryDto to Industry entity.
     *
     * @param industryDto the IndustryDto
     * @return the Industry entity
     */
    Industry toEntity(IndustryDto industryDto);

    /**
     * Convert Industry entity to IndustryView.
     *
     * @param industry the Industry entity
     * @return the IndustryView
     */
    IndustryView toView(Industry industry);
} 