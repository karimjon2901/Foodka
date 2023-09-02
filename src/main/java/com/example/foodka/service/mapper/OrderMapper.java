package com.example.foodka.service.mapper;

import com.example.foodka.dto.OrderDto;
import com.example.foodka.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class OrderMapper implements CommonMapper<OrderDto, Order>{

    @Override
    public abstract OrderDto toDto(Order order);

    @Override
    public abstract Order toEntity(OrderDto orderDto);
}
