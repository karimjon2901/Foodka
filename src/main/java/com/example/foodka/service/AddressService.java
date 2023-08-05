package com.example.foodka.service;

import com.example.foodka.dto.AddressDto;
import com.example.foodka.dto.ResponseDto;

import java.util.List;

public interface AddressService {
    ResponseDto<AddressDto> add(AddressDto addressDto);
    ResponseDto<AddressDto> getById(Integer id);
    ResponseDto<List<AddressDto>> getAllByUserId(Integer id);
    ResponseDto<Void> delete(Integer id);
}
