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
    date = "2025-03-01T22:30:14+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20250213-2037, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class SpecialityMapperImpl implements SpecialityMapper {

    @Override
    public SpecialityDto toDto(Speciality speciality) {
        if ( speciality == null ) {
            return null;
        }

        SpecialityDto.SpecialityDtoBuilder specialityDto = SpecialityDto.builder();

        specialityDto.description( speciality.getDescription() );
        specialityDto.id( speciality.getId() );
        specialityDto.name( speciality.getName() );

        return specialityDto.build();
    }

    @Override
    public Speciality toEntity(SpecialityDto specialityDto) {
        if ( specialityDto == null ) {
            return null;
        }

        Speciality.SpecialityBuilder speciality = Speciality.builder();

        speciality.description( specialityDto.getDescription() );
        speciality.id( specialityDto.getId() );
        speciality.name( specialityDto.getName() );

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

        specialityView.description( speciality.getDescription() );
        specialityView.id( speciality.getId() );
        specialityView.name( speciality.getName() );

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

        speciality.description( command.getDescription() );
        speciality.name( command.getName() );

        return speciality.build();
    }

    @Override
    public void updateEntityFromCommand(UpdateSpecialityCommand command, Speciality speciality) {
        if ( command == null ) {
            return;
        }

        speciality.setDescription( command.getDescription() );
        speciality.setName( command.getName() );
    }
}
