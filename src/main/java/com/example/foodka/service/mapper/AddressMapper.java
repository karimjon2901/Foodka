package com.example.foodka.service.mapper;

import com.example.foodka.dto.AddressDto;
import com.example.foodka.model.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class AddressMapper implements CommonMapper<AddressDto, Address>{
    @Override
    public abstract AddressDto toDto(Address address);

    @Override
    public abstract Address toEntity(AddressDto addressDto);
}
