package com.goodjob.benefit.mapper;

import com.goodjob.benefit.dto.BenefitDto;
import com.goodjob.benefit.entity.Benefit;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-05T00:03:04+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20250213-2037, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class BenefitMapperImpl implements BenefitMapper {

    @Override
    public BenefitDto toDto(Benefit benefit) {
        if ( benefit == null ) {
            return null;
        }

        BenefitDto.BenefitDtoBuilder benefitDto = BenefitDto.builder();

        benefitDto.id( benefit.getId() );
        benefitDto.type( benefit.getType() );

        return benefitDto.build();
    }

    @Override
    public Benefit toEntity(BenefitDto benefitDto) {
        if ( benefitDto == null ) {
            return null;
        }

        Benefit.BenefitBuilder benefit = Benefit.builder();

        benefit.id( benefitDto.getId() );
        benefit.type( benefitDto.getType() );

        return benefit.build();
    }

    @Override
    public List<BenefitDto> toDtoList(List<Benefit> benefits) {
        if ( benefits == null ) {
            return null;
        }

        List<BenefitDto> list = new ArrayList<BenefitDto>( benefits.size() );
        for ( Benefit benefit : benefits ) {
            list.add( toDto( benefit ) );
        }

        return list;
    }

    @Override
    public void updateBenefitFromDto(BenefitDto benefitDto, Benefit benefit) {
        if ( benefitDto == null ) {
            return;
        }

        benefit.setId( benefitDto.getId() );
        benefit.setType( benefitDto.getType() );
    }
}
