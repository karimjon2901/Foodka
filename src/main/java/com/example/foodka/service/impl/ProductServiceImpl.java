package com.example.foodka.service.impl;

import com.example.foodka.dto.ProductDto;
import com.example.foodka.dto.ResponseDto;
import com.example.foodka.model.Product;
import com.example.foodka.repository.ProductRepository;
import com.example.foodka.service.IdGenerator;
import com.example.foodka.service.ProductService;
import com.example.foodka.service.mapper.CategoryMapper;
import com.example.foodka.service.mapper.ProductMapper;
import com.example.foodka.service.mapper.TranslatorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.foodka.appStatus.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;
    private final IdGenerator idGenerator;
    private final TranslatorMapper translatorMapper;
    @Override
    public ResponseDto<ProductDto> addProduct(ProductDto productDto) {
        productDto.setId(idGenerator.generate());
        Product product = productMapper.toEntity(productDto);

        try{
            productRepository.save(product);

            return ResponseDto.<ProductDto>builder()
                    .success(true)
                    .message(OK)
                    .data(productMapper.toDto(product))
                    .build();
        } catch (Exception e){
            return ResponseDto.<ProductDto>builder()
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<ProductDto> updateProduct(ProductDto productDto) {
        if (productDto.getId() == null) {
            return ResponseDto.<ProductDto>builder()
                    .message(NULL_ID)
                    .build();
        }
        try {
            Optional<Product> optional = productRepository.findById(productDto.getId());

            if (optional.isEmpty()) {
                return ResponseDto.<ProductDto>builder()
                        .message(NOT_FOUND)
                        .build();
            }

            Product product = optional.get();

            product.setName(productDto.getName() != null ? translatorMapper.toEntity(productDto.getName()) : product.getName());
            product.setDescription(productDto.getDescription() != null ? translatorMapper.toEntity(productDto.getDescription()) : product.getDescription());
            product.setCategory(productDto.getCategory() != null ? categoryMapper.toEntity(productDto.getCategory()) : product.getCategory());
            product.setPrice(productDto.getPrice() != null ? productDto.getPrice() : product.getPrice());

            productRepository.save(product);

            return ResponseDto.<ProductDto>builder()
                    .message(OK)
                    .data(productMapper.toDto(product))
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<ProductDto>builder()
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<ProductDto> getProductById(String id) {
        if (id == null) {
            return ResponseDto.<ProductDto>builder()
                    .message(NULL_ID)
                    .build();
        }
        try {
            Optional<Product> byId = productRepository.findById(id);
            if (!byId.isEmpty()) {
                return ResponseDto.<ProductDto>builder()
                        .data(productMapper.toDto(byId.get()))
                        .success(true)
                        .message(OK)
                        .build();
            }
            return ResponseDto.<ProductDto>builder()
                    .message(NOT_FOUND)
                    .build();

        } catch (Exception e) {
            return ResponseDto.<ProductDto>builder()
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }

    }

    @Override
    public ResponseDto<List<ProductDto>> getProductsByCategoryId(String id) {
        if (id == null){
            return ResponseDto.<List<ProductDto>>builder()
                    .message(NULL_ID)
                    .build();
        }
        try{
            List<Product> allByCategoryId = productRepository.findAllByCategoryId(id);
            if (!allByCategoryId.isEmpty()){
                return ResponseDto.<List<ProductDto>>builder()
                        .message(OK)
                        .success(true)
                        .data(allByCategoryId.stream().map(productMapper::toDto).toList())
                        .build();
            }else {
                return ResponseDto.<List<ProductDto>>builder()
                        .message(NOT_FOUND)
                        .build();
            }
        }catch (Exception e){
            return ResponseDto.<List<ProductDto>>builder()
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<Page<ProductDto>> getAll(Integer size, Integer page) {
        Long count = productRepository.count();

        PageRequest pageRequest = PageRequest.of(
                (count / size) <= page ?
                        (count % size == 0 ? (int) (count / size) - 1
                                : (int) (count / size))
                        : page,
                size
        );

        try {
            Page<ProductDto> all = productRepository.findAll(pageRequest).map(productMapper::toDto);

            return ResponseDto.<Page<ProductDto>>builder()
                    .success(true)
                    .message(OK)
                    .data(all)
                    .build();
        } catch (Exception e){
            return ResponseDto.<Page<ProductDto>>builder()
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<Void> delete(String id) {
        if(id == null){
            return ResponseDto.<Void>builder()
                    .message(NULL_ID)
                    .build();
        }
        Optional<Product> optional = productRepository.findById(id);

        if(optional.isEmpty()){
            return ResponseDto.<Void>builder()
                    .message(NOT_FOUND)
                    .build();
        }

        try {
            productRepository.deleteById(id);

            return ResponseDto.<Void>builder()
                    .success(true)
                    .message(OK)
                    .build();
        } catch (Exception e){
            return ResponseDto.<Void>builder()
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }
}