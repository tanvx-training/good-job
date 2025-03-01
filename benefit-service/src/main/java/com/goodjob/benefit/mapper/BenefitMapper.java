package com.goodjob.benefit.mapper;

import com.goodjob.benefit.dto.BenefitDto;
import com.goodjob.benefit.entity.Benefit;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Mapper for converting between Benefit entity and BenefitDto.
 */
@Mapper(componentModel = "spring")
public interface BenefitMapper {

    /**
     * Convert Benefit entity to BenefitDto.
     *
     * @param benefit the Benefit entity
     * @return the BenefitDto
     */
    BenefitDto toDto(Benefit benefit);

    /**
     * Convert BenefitDto to Benefit entity.
     *
     * @param benefitDto the BenefitDto
     * @return the Benefit entity
     */
    Benefit toEntity(BenefitDto benefitDto);

    /**
     * Convert a list of Benefit entities to a list of BenefitDtos.
     *
     * @param benefits the list of Benefit entities
     * @return the list of BenefitDtos
     */
    List<BenefitDto> toDtoList(List<Benefit> benefits);

    /**
     * Update a Benefit entity with data from a BenefitDto.
     *
     * @param benefitDto the BenefitDto with updated data
     * @param benefit the Benefit entity to update
     */
    void updateBenefitFromDto(BenefitDto benefitDto, @MappingTarget Benefit benefit);
} 