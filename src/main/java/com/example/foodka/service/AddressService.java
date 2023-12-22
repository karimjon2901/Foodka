package com.example.foodka.service;

import com.example.foodka.dto.AddressDto;
import com.example.foodka.dto.ResponseDto;

import java.util.List;

public interface AddressService {
    ResponseDto<AddressDto> add(AddressDto addressDto);
    ResponseDto<AddressDto> getById(String id);
    ResponseDto<List<AddressDto>> getAllByUserId(String id);
    ResponseDto<Void> delete(String id);
}
