package com.goodjob.skill.mapper;

import com.goodjob.skill.command.dto.CreateSkillCommand;
import com.goodjob.skill.command.dto.UpdateSkillCommand;
import com.goodjob.skill.dto.SkillDto;
import com.goodjob.skill.entity.Skill;
import com.goodjob.skill.query.dto.SkillView;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-02T22:41:09+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20250213-2037, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class SkillMapperImpl implements SkillMapper {

    @Override
    public SkillDto toDto(Skill skill) {
        if ( skill == null ) {
            return null;
        }

        SkillDto.SkillDtoBuilder skillDto = SkillDto.builder();

        skillDto.abbreviation( skill.getAbbreviation() );
        skillDto.id( skill.getId() );
        skillDto.name( skill.getName() );

        return skillDto.build();
    }

    @Override
    public Skill toEntity(SkillDto skillDto) {
        if ( skillDto == null ) {
            return null;
        }

        Skill.SkillBuilder skill = Skill.builder();

        skill.abbreviation( skillDto.getAbbreviation() );
        skill.id( skillDto.getId() );
        skill.name( skillDto.getName() );

        return skill.build();
    }

    @Override
    public List<SkillDto> toDtoList(List<Skill> skills) {
        if ( skills == null ) {
            return null;
        }

        List<SkillDto> list = new ArrayList<SkillDto>( skills.size() );
        for ( Skill skill : skills ) {
            list.add( toDto( skill ) );
        }

        return list;
    }

    @Override
    public SkillView toView(Skill skill) {
        if ( skill == null ) {
            return null;
        }

        SkillView.SkillViewBuilder skillView = SkillView.builder();

        skillView.abbreviation( skill.getAbbreviation() );
        skillView.id( skill.getId() );
        skillView.name( skill.getName() );

        return skillView.build();
    }

    @Override
    public List<SkillView> toViewList(List<Skill> skills) {
        if ( skills == null ) {
            return null;
        }

        List<SkillView> list = new ArrayList<SkillView>( skills.size() );
        for ( Skill skill : skills ) {
            list.add( toView( skill ) );
        }

        return list;
    }

    @Override
    public Skill toEntity(CreateSkillCommand command) {
        if ( command == null ) {
            return null;
        }

        Skill.SkillBuilder skill = Skill.builder();

        skill.abbreviation( command.getAbbreviation() );
        skill.name( command.getName() );

        return skill.build();
    }

    @Override
    public void updateEntityFromCommand(UpdateSkillCommand command, Skill skill) {
        if ( command == null ) {
            return;
        }

        skill.setAbbreviation( command.getAbbreviation() );
        skill.setName( command.getName() );
    }
}
