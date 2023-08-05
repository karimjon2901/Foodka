package com.example.foodka.service.mapper;

import com.example.foodka.dto.UsersDto;
import com.example.foodka.model.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UsersMapper implements CommonMapper<UsersDto, Users>{
    @Override
    public abstract UsersDto toDto(Users users);

    @Override
    public abstract Users toEntity(UsersDto usersDto);
}
