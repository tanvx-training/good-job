package com.goodjob.skill.mapper;

import com.goodjob.skill.command.dto.CreateSkillCommand;
import com.goodjob.skill.command.dto.UpdateSkillCommand;
import com.goodjob.skill.dto.SkillDto;
import com.goodjob.skill.entity.Skill;
import com.goodjob.skill.query.dto.SkillView;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper for converting between Skill entity and DTOs.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SkillMapper {

    /**
     * Converts a Skill entity to a SkillDto.
     *
     * @param skill the Skill entity
     * @return the SkillDto
     */
    SkillDto toDto(Skill skill);

    /**
     * Converts a SkillDto to a Skill entity.
     *
     * @param skillDto the SkillDto
     * @return the Skill entity
     */
    Skill toEntity(SkillDto skillDto);

    /**
     * Converts a list of Skill entities to a list of SkillDtos.
     *
     * @param skills the list of Skill entities
     * @return the list of SkillDtos
     */
    List<SkillDto> toDtoList(List<Skill> skills);
    
    /**
     * Converts a Skill entity to a SkillView.
     *
     * @param skill the Skill entity
     * @return the SkillView
     */
    SkillView toView(Skill skill);
    
    /**
     * Converts a list of Skill entities to a list of SkillViews.
     *
     * @param skills the list of Skill entities
     * @return the list of SkillViews
     */
    List<SkillView> toViewList(List<Skill> skills);
    
    /**
     * Converts a CreateSkillCommand to a Skill entity.
     *
     * @param command the CreateSkillCommand
     * @return the Skill entity
     */
    Skill toEntity(CreateSkillCommand command);
    
    /**
     * Updates a Skill entity with values from an UpdateSkillCommand.
     *
     * @param command the UpdateSkillCommand
     * @param skill the Skill entity to update
     */
    void updateEntityFromCommand(UpdateSkillCommand command, @MappingTarget Skill skill);
} 