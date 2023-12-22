package com.example.foodka.service;

import com.example.foodka.dto.OrdersDto;
import com.example.foodka.dto.ResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrdersService {
    ResponseDto<OrdersDto> add(OrdersDto ordersDto);
    ResponseDto<Page<OrdersDto>> getAll(Integer size, Integer page);
    ResponseDto<OrdersDto> getById(String id);
    ResponseDto<List<OrdersDto>> getByUserId(String id);
    ResponseDto<OrdersDto> update(OrdersDto ordersDto);
}
