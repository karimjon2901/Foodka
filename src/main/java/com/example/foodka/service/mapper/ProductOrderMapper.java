package com.example.foodka.service.mapper;

import com.example.foodka.dto.ProductOrderDto;
import com.example.foodka.model.ProductOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ProductOrderMapper implements CommonMapper<ProductOrderDto, ProductOrder>{
    @Override
    public abstract ProductOrderDto toDto(ProductOrder productOrder);

    @Override
    public abstract ProductOrder toEntity(ProductOrderDto productOrderDto);
}
