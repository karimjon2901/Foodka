package com.example.foodka.service;

import com.example.foodka.dto.CategoryDto;
import com.example.foodka.dto.ResponseDto;

import java.util.List;

public interface CategoryService {
    ResponseDto<CategoryDto> addCategory(CategoryDto categoryDto);
    ResponseDto<CategoryDto> getById(String id);
    ResponseDto<List<CategoryDto>> getAll();
    ResponseDto<Void> delete (String id);
}
