package com.example.foodka.service;

import com.example.foodka.dto.OrderDto;
import com.example.foodka.dto.ResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    ResponseDto<OrderDto> add(OrderDto orderDto);
    ResponseDto<Page<OrderDto>> getAll(Integer size, Integer page);
    ResponseDto<OrderDto> getById(Integer id);
    ResponseDto<List<OrderDto>> getByUserId(Integer id);
    ResponseDto<OrderDto> update(OrderDto orderDto);
}
