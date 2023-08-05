package com.example.foodka.service;

import com.example.foodka.dto.ProductDto;
import com.example.foodka.dto.ResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ResponseDto<ProductDto> addProduct(ProductDto productDto);
    ResponseDto<ProductDto> updateProduct(ProductDto productDto);
    ResponseDto<ProductDto> getProductById(Integer id);
    ResponseDto<List<ProductDto>> getProductsByCategoryId(Integer id);
    ResponseDto<Page<ProductDto>> getAll(Integer size, Integer page);
    ResponseDto<Void> delete(Integer id);
}
