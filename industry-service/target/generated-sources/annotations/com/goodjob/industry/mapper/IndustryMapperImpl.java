package com.goodjob.industry.mapper;

import com.goodjob.industry.dto.IndustryDto;
import com.goodjob.industry.entity.Industry;
import com.goodjob.industry.query.dto.IndustryView;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-01T22:30:13+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20250213-2037, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class IndustryMapperImpl implements IndustryMapper {

    @Override
    public IndustryDto toDto(Industry industry) {
        if ( industry == null ) {
            return null;
        }

        IndustryDto.IndustryDtoBuilder industryDto = IndustryDto.builder();

        industryDto.id( industry.getId() );
        industryDto.name( industry.getName() );

        return industryDto.build();
    }

    @Override
    public Industry toEntity(IndustryDto industryDto) {
        if ( industryDto == null ) {
            return null;
        }

        Industry.IndustryBuilder industry = Industry.builder();

        industry.id( industryDto.getId() );
        industry.name( industryDto.getName() );

        return industry.build();
    }

    @Override
    public IndustryView toView(Industry industry) {
        if ( industry == null ) {
            return null;
        }

        IndustryView.IndustryViewBuilder industryView = IndustryView.builder();

        industryView.id( industry.getId() );
        industryView.name( industry.getName() );

        return industryView.build();
    }
}
