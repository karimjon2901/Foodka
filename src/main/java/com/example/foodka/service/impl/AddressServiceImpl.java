package com.example.foodka.service.impl;

import com.example.foodka.dto.AddressDto;
import com.example.foodka.dto.ResponseDto;
import com.example.foodka.model.Address;
import com.example.foodka.repository.AddressRepository;
import com.example.foodka.service.AddressService;
import com.example.foodka.service.IdGenerator;
import com.example.foodka.service.mapper.AddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.foodka.appStatus.AppStatusMessages.*;
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final IdGenerator idGenerator;
    @Override
    public ResponseDto<AddressDto> add(AddressDto addressDto) {
        try{
            addressDto.setId(idGenerator.generate());
            Address address = addressMapper.toEntity(addressDto);
            addressRepository.save(address);

            return ResponseDto.<AddressDto>builder()
                    .message(OK)
                    .success(true)
                    .data(addressDto)
                    .build();
        }catch (Exception e){
            return ResponseDto.<AddressDto>builder()
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<AddressDto> getById(String id) {
        try {
            return addressRepository.findById(id)
                    .map(u -> ResponseDto.<AddressDto>builder()
                            .data(addressMapper.toDto(u))
                            .success(true)
                            .message(OK)
                            .build())
                    .orElse(ResponseDto.<AddressDto>builder()
                            .message(NOT_FOUND)
                            .build());
        }catch (Exception e){
            return ResponseDto.<AddressDto>builder()
                    .message(DATABASE_ERROR + e.getMessage())
                    .success(true)
                    .build();
        }
    }

    @Override
    public ResponseDto<List<AddressDto>> getAllByUserId(String id) {
        try{
            return ResponseDto.<List<AddressDto>>builder()
                    .message(OK)
                    .success(true)
                    .data(addressRepository.findAllByUserId(id).stream().map(addressMapper::toDto).toList())
                    .build();
        }catch (Exception e){
            return ResponseDto.<List<AddressDto>>builder()
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<Void> delete(String id) {
        if (id == null) {
            return ResponseDto.<Void>builder()
                    .message(NULL_ID)
                    .build();
        }
        try{
            Optional<Address> byId = addressRepository.findById(id);

            if (byId.isEmpty()){
                return ResponseDto.<Void>builder()
                        .message(NOT_FOUND)
                        .build();
            }

            addressRepository.deleteById(id);

            return ResponseDto.<Void>builder()
                    .message(OK)
                    .success(true)
                    .build();
        } catch (Exception e){
            return ResponseDto.<Void>builder()
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }
}
