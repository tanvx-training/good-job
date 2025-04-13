package com.goodjob.company.infrastructure.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressDto {

    private String country;
    private String state;
    private String city;
    private String zipCode;
    private String address;
}
