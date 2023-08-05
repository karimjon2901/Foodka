package com.example.foodka.service.impl;

import com.example.foodka.dto.ResponseDto;
import com.example.foodka.dto.UsersDto;
import com.example.foodka.model.Users;
import com.example.foodka.repository.UsersRepository;
import com.example.foodka.service.UsersService;
import com.example.foodka.service.mapper.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.foodka.appStatus.AppStatusCodes.*;
import static com.example.foodka.appStatus.AppStatusMessages.*;
@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;
    @Override
    public ResponseDto<UsersDto> add(UsersDto usersDto) {
        try{
            Users user = usersMapper.toEntity(usersDto);
            usersRepository.save(user);

            return ResponseDto.<UsersDto>builder()
                    .message(OK)
                    .code(OK_CODE)
                    .success(true)
                    .data(usersDto)
                    .build();
        }catch (Exception e){
            return ResponseDto.<UsersDto>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + " : " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<UsersDto> getById(Integer id) {
        if (id == null){
            return ResponseDto.<UsersDto>builder()
                    .code(VALIDATION_ERROR_CODE)
                    .message("Id is null!")
                    .build();
        }
        try {
            return usersRepository.findById(id)
                    .map(u -> ResponseDto.<UsersDto>builder()
                            .data(usersMapper.toDto(u))
                            .success(true)
                            .message(OK)
                            .build())
                    .orElse(ResponseDto.<UsersDto>builder()
                            .message(NOT_FOUND)
                            .code(NOT_FOUND_ERROR_CODE)
                            .build());
        }catch (Exception e){
            return ResponseDto.<UsersDto>builder()
                    .message(e.getMessage())
                    .success(true)
                    .code(DATABASE_ERROR_CODE)
                    .build();
        }
    }

    @Override
    public ResponseDto<List<UsersDto>> getAll() {
        try{
            return ResponseDto.<List<UsersDto>>builder()
                    .message(OK)
                    .code(OK_CODE)
                    .success(true)
                    .data(usersRepository.findAll().stream().map(usersMapper::toDto).toList())
                    .build();
        }catch (Exception e){
            return ResponseDto.<List<UsersDto>>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + " : " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<UsersDto> update(UsersDto usersDto) {
        if (usersDto.getId() == null){
            return ResponseDto.<UsersDto>builder()
                    .message(NULL_VALUE)
                    .code(VALIDATION_ERROR_CODE)
                    .data(usersDto)
                    .build();
        }

        Optional<Users> userOptional = usersRepository.findById(usersDto.getId());

        if (userOptional.isEmpty()){
            return ResponseDto.<UsersDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_ERROR_CODE)
                    .data(usersDto)
                    .build();
        }
        Users user = userOptional.get();
        if (usersDto.getName() != null) {
            user.setName(usersDto.getName());
        }
        if (usersDto.getPassword() != null) {
            user.setPassword(usersDto.getPassword());
        }
        if (usersDto.getPhoneNumber() != null) {
            Optional<Users> user1 = usersRepository.findFirstByPhoneNumber(usersDto.getPhoneNumber());
            if (user1.isEmpty()){
                user.setPhoneNumber(usersDto.getPhoneNumber());
            }
            else {
                return ResponseDto.<UsersDto>builder()
                        .message("This phone number already exists!")
                        .code(VALIDATION_ERROR_CODE)
                        .build();
            }
        }

        try {
            usersRepository.save(user);

            return ResponseDto.<UsersDto>builder()
                    .data(usersMapper.toDto(user))
                    .code(OK_CODE)
                    .success(true)
                    .message(OK)
                    .build();
        }catch (Exception e){
            return ResponseDto.<UsersDto>builder()
                    .data(usersMapper.toDto(user))
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<UsersDto> getUserByPhoneNumber(String phoneNumber) {
        try {
            return usersRepository.findFirstByPhoneNumber(phoneNumber)
                    .map(u -> ResponseDto.<UsersDto>builder()
                            .data(usersMapper.toDto(u))
                            .success(true)
                            .message(OK)
                            .build())
                    .orElse(ResponseDto.<UsersDto>builder()
                            .message(NOT_FOUND)
                            .code(NOT_FOUND_ERROR_CODE)
                            .build());
        }catch (Exception e){
            return ResponseDto.<UsersDto>builder()
                    .message(e.getMessage())
                    .success(true)
                    .code(DATABASE_ERROR_CODE)
                    .build();
        }
    }
}