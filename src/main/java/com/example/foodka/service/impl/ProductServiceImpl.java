package com.example.foodka.service.impl;

import com.example.foodka.dto.ProductDto;
import com.example.foodka.dto.ResponseDto;
import com.example.foodka.model.Product;
import com.example.foodka.repository.ProductRepository;
import com.example.foodka.service.ProductService;
import com.example.foodka.service.mapper.CategoryMapper;
import com.example.foodka.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.foodka.appStatus.AppStatusCodes.*;
import static com.example.foodka.appStatus.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;
    @Override
    public ResponseDto<ProductDto> addProduct(ProductDto productDto) {
        return null;
    }

    @Override
    public ResponseDto<ProductDto> updateProduct(ProductDto productDto) {
        if (productDto.getId() == null) {
            return ResponseDto.<ProductDto>builder()
                    .message("Product ID is null")
                    .code(VALIDATION_ERROR_CODE)
                    .build();
        }

        Optional<Product> optional = productRepository.findById(productDto.getId());

        if (optional.isEmpty()) {
            return ResponseDto.<ProductDto>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .build();
        }

        Product product = optional.get();

        if (productDto.getName() != null) {
            product.setName(productDto.getName());
        }
        if (productDto.getDescription() != null) {
            product.setDescription(productDto.getDescription());
        }
        if (productDto.getCategory() != null) {
            product.setCategory(categoryMapper.toEntity(productDto.getCategory()));
        }
        if (productDto.getPrice() != null) {
            product.setPrice(productDto.getPrice());
        }

        try {
            productRepository.save(product);

            return ResponseDto.<ProductDto>builder()
                    .message(OK)
                    .data(productMapper.toDto(product))
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<ProductDto>builder()
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .code(DATABASE_ERROR_CODE)
                    .build();
        }
    }

    @Override
    public ResponseDto<ProductDto> getProductById(Integer id) {
        if (id == null) {
            return ResponseDto.<ProductDto>builder()
                    .message("Product ID is null")
                    .code(VALIDATION_ERROR_CODE)
                    .build();
        }
        try {
            Optional<Product> byId = productRepository.findById(id);
            if (!byId.isEmpty()) {
                return ResponseDto.<ProductDto>builder()
                        .data(productMapper.toDto(byId.get()))
                        .success(true)
                        .code(OK_CODE)
                        .message(OK)
                        .build();
            }
            return ResponseDto.<ProductDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_ERROR_CODE)
                    .build();

        } catch (Exception e) {
            return ResponseDto.<ProductDto>builder()
                    .message(DATABASE_ERROR)
                    .code(DATABASE_ERROR_CODE)
                    .build();
        }

    }

    @Override
    public ResponseDto<Page<ProductDto>> getAll(Integer size, Integer page) {
        return null;
    }

    @Override
    public ResponseDto<Void> delete(Integer id) {
        return null;
    }
}
