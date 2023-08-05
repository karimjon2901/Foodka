package com.example.foodka.service.mapper;

import com.example.foodka.dto.ProductDto;
import com.example.foodka.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ProductMapper implements CommonMapper<ProductDto, Product>{
    @Override
    public abstract ProductDto toDto(Product product);

    @Override
    public abstract Product toEntity(ProductDto productDto);
}
