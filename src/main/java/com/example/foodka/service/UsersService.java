package com.example.foodka.service;

import com.example.foodka.dto.AddressDto;
import com.example.foodka.dto.ResponseDto;
import com.example.foodka.dto.UsersDto;

import java.util.List;

public interface UsersService {
    ResponseDto<UsersDto> add(UsersDto usersDto);
    ResponseDto<UsersDto> getById(Integer id);
    ResponseDto<List<UsersDto>> getAll();
    ResponseDto<UsersDto> update(UsersDto usersDto);
    ResponseDto<UsersDto> getUserByPhoneNumber(String phoneNumber);
    ResponseDto<UsersDto> addAddress(AddressDto addressDto);
}
