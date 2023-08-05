package com.example.foodka.service;

import com.example.foodka.dto.CategoryDto;
import com.example.foodka.dto.ResponseDto;

public interface CategoryService {
    ResponseDto<CategoryDto> addCategory(CategoryDto categoryDto);
    ResponseDto<Void> delete (Integer id);
}
