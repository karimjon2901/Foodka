package com.example.foodka.service.impl;

import com.example.foodka.dto.OrderDto;
import com.example.foodka.dto.ResponseDto;
import com.example.foodka.model.Order;
import com.example.foodka.repository.OrderRepository;
import com.example.foodka.service.OrderService;
import com.example.foodka.service.mapper.OrderMapper;
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
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public ResponseDto<OrderDto> add(OrderDto orderDto) {
        Order order = orderMapper.toEntity(orderDto);

        try{
            orderRepository.save(order);

            return ResponseDto.<OrderDto>builder()
                    .success(true)
                    .message(OK)
                    .data(orderMapper.toDto(order))
                    .build();
        } catch (Exception e){
            return ResponseDto.<OrderDto>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + " -> " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<Page<OrderDto>> getAll(Integer size, Integer page) {
        Long count = orderRepository.count();

        PageRequest pageRequest = PageRequest.of(
                (count / size) <= page ?
                        (count % size == 0 ? (int) (count / size) - 1
                                : (int) (count / size))
                        : page,
                size
        );

        try {
            Page<OrderDto> all = orderRepository.findAll(pageRequest).map(orderMapper::toDto);

            return ResponseDto.<Page<OrderDto>>builder()
                    .success(true)
                    .message(OK)
                    .data(all)
                    .build();
        } catch (Exception e){
            return ResponseDto.<Page<OrderDto>>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + " -> " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<OrderDto> getById(Integer id) {
        if (id == null) {
            return ResponseDto.<OrderDto>builder()
                    .message("Id is null")
                    .code(VALIDATION_ERROR_CODE)
                    .build();
        }
        try {
            Optional<Order> byId = orderRepository.findById(id);
            if (!byId.isEmpty()) {
                return ResponseDto.<OrderDto>builder()
                        .data(orderMapper.toDto(byId.get()))
                        .success(true)
                        .code(OK_CODE)
                        .message(OK)
                        .build();
            }
            return ResponseDto.<OrderDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_ERROR_CODE)
                    .build();

        } catch (Exception e) {
            return ResponseDto.<OrderDto>builder()
                    .message(DATABASE_ERROR)
                    .code(DATABASE_ERROR_CODE)
                    .build();
        }
    }

    @Override
    public ResponseDto<List<OrderDto>> getByUserId(Integer id) {
        if (id == null){
            return ResponseDto.<List<OrderDto>>builder()
                    .code(VALIDATION_ERROR_CODE)
                    .message("Id is null!")
                    .build();
        }
        try{
            List<Order> allByCategoryId = orderRepository.findAllByUserId(id);
            if (!allByCategoryId.isEmpty()){
                return ResponseDto.<List<OrderDto>>builder()
                        .message(OK)
                        .code(OK_CODE)
                        .success(true)
                        .data(allByCategoryId.stream().map(orderMapper::toDto).toList())
                        .build();
            }else {
                return ResponseDto.<List<OrderDto>>builder()
                        .code(NOT_FOUND_ERROR_CODE)
                        .message(NOT_FOUND)
                        .build();
            }
        }catch (Exception e){
            return ResponseDto.<List<OrderDto>>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + " : " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<OrderDto> update(OrderDto orderDto) {
        if (orderDto.getId() == null) {
            return ResponseDto.<OrderDto>builder()
                    .message("Product ID is null")
                    .code(VALIDATION_ERROR_CODE)
                    .build();
        }

        Optional<Order> optional = orderRepository.findById(orderDto.getId());

        if (optional.isEmpty()) {
            return ResponseDto.<OrderDto>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .build();
        }

        Order order = optional.get();

        if (orderDto.getDescription() != null) {
            order.setDescription(orderDto.getDescription());
        }
        if (orderDto.getPrice() != null) {
            order.setPrice(orderDto.getPrice());
        }
        if (orderDto.getTime() != null){
            order.setTime(orderDto.getTime());
        }
        if (orderDto.getStatus() != null) {
            order.setStatus(orderDto.getStatus());
        }

        try {
            orderRepository.save(order);

            return ResponseDto.<OrderDto>builder()
                    .message(OK)
                    .data(orderMapper.toDto(order))
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<OrderDto>builder()
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .code(DATABASE_ERROR_CODE)
                    .build();
        }
    }
}
