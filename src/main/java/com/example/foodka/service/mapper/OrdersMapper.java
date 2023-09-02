package com.example.foodka.service.mapper;

import com.example.foodka.dto.OrdersDto;
import com.example.foodka.model.Orders;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class OrdersMapper implements CommonMapper<OrdersDto, Orders>{

    @Override
    public abstract OrdersDto toDto(Orders orders);

    @Override
    public abstract Orders toEntity(OrdersDto ordersDto);
}
