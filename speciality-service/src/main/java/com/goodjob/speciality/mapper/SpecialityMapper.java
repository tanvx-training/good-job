package com.goodjob.speciality.mapper;

import com.goodjob.speciality.command.dto.CreateSpecialityCommand;
import com.goodjob.speciality.command.dto.UpdateSpecialityCommand;
import com.goodjob.speciality.dto.SpecialityDto;
import com.goodjob.speciality.entity.Speciality;
import com.goodjob.speciality.query.dto.SpecialityView;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper for converting between Speciality entity and DTOs.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SpecialityMapper {

    /**
     * Converts a Speciality entity to a SpecialityDto.
     *
     * @param speciality the Speciality entity
     * @return the SpecialityDto
     */
    SpecialityDto toDto(Speciality speciality);

    /**
     * Converts a SpecialityDto to a Speciality entity.
     *
     * @param specialityDto the SpecialityDto
     * @return the Speciality entity
     */
    Speciality toEntity(SpecialityDto specialityDto);

    /**
     * Converts a list of Speciality entities to a list of SpecialityDtos.
     *
     * @param specialities the list of Speciality entities
     * @return the list of SpecialityDtos
     */
    List<SpecialityDto> toDtoList(List<Speciality> specialities);
    
    /**
     * Converts a Speciality entity to a SpecialityView.
     *
     * @param speciality the Speciality entity
     * @return the SpecialityView
     */
    SpecialityView toView(Speciality speciality);
    
    /**
     * Converts a list of Speciality entities to a list of SpecialityViews.
     *
     * @param specialities the list of Speciality entities
     * @return the list of SpecialityViews
     */
    List<SpecialityView> toViewList(List<Speciality> specialities);
    
    /**
     * Converts a CreateSpecialityCommand to a Speciality entity.
     *
     * @param command the CreateSpecialityCommand
     * @return the Speciality entity
     */
    Speciality toEntity(CreateSpecialityCommand command);
    
    /**
     * Updates a Speciality entity with values from an UpdateSpecialityCommand.
     *
     * @param command the UpdateSpecialityCommand
     * @param speciality the Speciality entity to update
     */
    void updateEntityFromCommand(UpdateSpecialityCommand command, @MappingTarget Speciality speciality);
} 