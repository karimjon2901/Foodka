package com.example.foodka.service.impl;

import com.example.foodka.dto.OrdersDto;
import com.example.foodka.dto.ResponseDto;
import com.example.foodka.model.Orders;
import com.example.foodka.repository.OrdersRepository;
import com.example.foodka.service.OrdersService;
import com.example.foodka.service.mapper.OrdersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.foodka.appStatus.AppStatusCodes.*;
import static com.example.foodka.appStatus.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {
    private final OrdersRepository ordersRepository;
    private final OrdersMapper ordersMapper;

    @Override
    public ResponseDto<OrdersDto> add(OrdersDto ordersDto) {
        Orders orders = ordersMapper.toEntity(ordersDto);

        try{
            ordersRepository.save(orders);

            return ResponseDto.<OrdersDto>builder()
                    .success(true)
                    .message(OK)
                    .data(ordersMapper.toDto(orders))
                    .build();
        } catch (Exception e){
            return ResponseDto.<OrdersDto>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + " -> " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<Page<OrdersDto>> getAll(Integer size, Integer page) {
        Long count = ordersRepository.count();

        PageRequest pageRequest = PageRequest.of(
                (count / size) <= page ?
                        (count % size == 0 ? (int) (count / size) - 1
                                : (int) (count / size))
                        : page,
                size
        );

        try {
            Page<OrdersDto> all = ordersRepository.findAll(pageRequest).map(ordersMapper::toDto);

            return ResponseDto.<Page<OrdersDto>>builder()
                    .success(true)
                    .message(OK)
                    .data(all)
                    .build();
        } catch (Exception e){
            return ResponseDto.<Page<OrdersDto>>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + " -> " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<OrdersDto> getById(Integer id) {
        if (id == null) {
            return ResponseDto.<OrdersDto>builder()
                    .message("Id is null")
                    .code(VALIDATION_ERROR_CODE)
                    .build();
        }
        try {
            Optional<Orders> byId = ordersRepository.findById(id);
            if (!byId.isEmpty()) {
                return ResponseDto.<OrdersDto>builder()
                        .data(ordersMapper.toDto(byId.get()))
                        .success(true)
                        .code(OK_CODE)
                        .message(OK)
                        .build();
            }
            return ResponseDto.<OrdersDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_ERROR_CODE)
                    .build();

        } catch (Exception e) {
            return ResponseDto.<OrdersDto>builder()
                    .message(DATABASE_ERROR)
                    .code(DATABASE_ERROR_CODE)
                    .build();
        }
    }

    @Override
    public ResponseDto<List<OrdersDto>> getByUserId(Integer id) {
        if (id == null){
            return ResponseDto.<List<OrdersDto>>builder()
                    .code(VALIDATION_ERROR_CODE)
                    .message("Id is null!")
                    .build();
        }
        try{
            List<Orders> allByCategoryId = ordersRepository.findAllByUserId(id);
            if (!allByCategoryId.isEmpty()){
                return ResponseDto.<List<OrdersDto>>builder()
                        .message(OK)
                        .code(OK_CODE)
                        .success(true)
                        .data(allByCategoryId.stream().map(ordersMapper::toDto).toList())
                        .build();
            }else {
                return ResponseDto.<List<OrdersDto>>builder()
                        .code(NOT_FOUND_ERROR_CODE)
                        .message(NOT_FOUND)
                        .build();
            }
        }catch (Exception e){
            return ResponseDto.<List<OrdersDto>>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + " : " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<OrdersDto> update(OrdersDto ordersDto) {
        if (ordersDto.getId() == null) {
            return ResponseDto.<OrdersDto>builder()
                    .message("Product ID is null")
                    .code(VALIDATION_ERROR_CODE)
                    .build();
        }

        Optional<Orders> optional = ordersRepository.findById(ordersDto.getId());

        if (optional.isEmpty()) {
            return ResponseDto.<OrdersDto>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .build();
        }

        Orders orders = optional.get();

        if (ordersDto.getDescription() != null) {
            orders.setDescription(ordersDto.getDescription());
        }
        if (ordersDto.getPrice() != null) {
            orders.setPrice(ordersDto.getPrice());
        }
        if (ordersDto.getTime() != null){
            orders.setTime(ordersDto.getTime());
        }
        if (ordersDto.getStatus() != null) {
            orders.setStatus(ordersDto.getStatus());
        }

        try {
            ordersRepository.save(orders);

            return ResponseDto.<OrdersDto>builder()
                    .message(OK)
                    .data(ordersMapper.toDto(orders))
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<OrdersDto>builder()
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .code(DATABASE_ERROR_CODE)
                    .build();
        }
    }
}
