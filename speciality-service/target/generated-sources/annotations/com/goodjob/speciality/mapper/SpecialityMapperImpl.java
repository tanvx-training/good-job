package com.goodjob.speciality.mapper;

import com.goodjob.speciality.command.dto.CreateSpecialityCommand;
import com.goodjob.speciality.command.dto.UpdateSpecialityCommand;
import com.goodjob.speciality.dto.SpecialityDto;
import com.goodjob.speciality.entity.Speciality;
import com.goodjob.speciality.query.dto.SpecialityView;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-16T00:21:26+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class SpecialityMapperImpl implements SpecialityMapper {

    @Override
    public SpecialityDto toDto(Speciality speciality) {
        if ( speciality == null ) {
            return null;
        }

        SpecialityDto.SpecialityDtoBuilder specialityDto = SpecialityDto.builder();

        specialityDto.id( speciality.getId() );
        specialityDto.name( speciality.getName() );
        specialityDto.description( speciality.getDescription() );

        return specialityDto.build();
    }

    @Override
    public Speciality toEntity(SpecialityDto specialityDto) {
        if ( specialityDto == null ) {
            return null;
        }

        Speciality.SpecialityBuilder speciality = Speciality.builder();

        speciality.id( specialityDto.getId() );
        speciality.name( specialityDto.getName() );
        speciality.description( specialityDto.getDescription() );

        return speciality.build();
    }

    @Override
    public List<SpecialityDto> toDtoList(List<Speciality> specialities) {
        if ( specialities == null ) {
            return null;
        }

        List<SpecialityDto> list = new ArrayList<SpecialityDto>( specialities.size() );
        for ( Speciality speciality : specialities ) {
            list.add( toDto( speciality ) );
        }

        return list;
    }

    @Override
    public SpecialityView toView(Speciality speciality) {
        if ( speciality == null ) {
            return null;
        }

        SpecialityView.SpecialityViewBuilder specialityView = SpecialityView.builder();

        specialityView.id( speciality.getId() );
        specialityView.name( speciality.getName() );
        specialityView.description( speciality.getDescription() );

        return specialityView.build();
    }

    @Override
    public List<SpecialityView> toViewList(List<Speciality> specialities) {
        if ( specialities == null ) {
            return null;
        }

        List<SpecialityView> list = new ArrayList<SpecialityView>( specialities.size() );
        for ( Speciality speciality : specialities ) {
            list.add( toView( speciality ) );
        }

        return list;
    }

    @Override
    public Speciality toEntity(CreateSpecialityCommand command) {
        if ( command == null ) {
            return null;
        }

        Speciality.SpecialityBuilder speciality = Speciality.builder();

        speciality.name( command.getName() );
        speciality.description( command.getDescription() );

        return speciality.build();
    }

    @Override
    public void updateEntityFromCommand(UpdateSpecialityCommand command, Speciality speciality) {
        if ( command == null ) {
            return;
        }

        speciality.setName( command.getName() );
        speciality.setDescription( command.getDescription() );
    }
}
