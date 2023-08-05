package com.example.foodka.service.mapper;

import com.example.foodka.dto.CategoryDto;
import com.example.foodka.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class CategoryMapper implements CommonMapper<CategoryDto, Category>{
    @Override
    public abstract CategoryDto toDto(Category category);

    @Override
    public abstract Category toEntity(CategoryDto categoryDto);
}
