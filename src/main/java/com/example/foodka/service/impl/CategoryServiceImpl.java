package com.example.foodka.service.impl;

import com.example.foodka.dto.CategoryDto;
import com.example.foodka.dto.ResponseDto;
import com.example.foodka.model.Category;
import com.example.foodka.repository.CategoryRepository;
import com.example.foodka.service.CategoryService;
import com.example.foodka.service.IdGenerator;
import com.example.foodka.service.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.foodka.appStatus.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final IdGenerator idGenerator;
    @Override
    public ResponseDto<CategoryDto> addCategory(CategoryDto categoryDto) {
        try {
            categoryDto.setId(idGenerator.generate());
            return ResponseDto.<CategoryDto>builder()
                    .data(categoryMapper.toDto(
                            categoryRepository.save(
                                    categoryMapper.toEntity(categoryDto)
                            )
                    ))
                    .message(OK)
                    .success(true)
                    .build();
        }catch (Exception e){
            return ResponseDto.<CategoryDto>builder()
                    .message(DATABASE_ERROR + e.getMessage())
                    .data(categoryDto)
                    .build();
        }
    }

    @Override
    public ResponseDto<CategoryDto> getById(String id) {
        if (id == null){
            return ResponseDto.<CategoryDto>builder()
                    .message(NULL_ID)
                    .build();
        }
        try {
            return categoryRepository.findById(id)
                    .map(u -> ResponseDto.<CategoryDto>builder()
                            .data(categoryMapper.toDto(u))
                            .success(true)
                            .message(OK)
                            .build())
                    .orElse(ResponseDto.<CategoryDto>builder()
                            .message(NOT_FOUND)
                            .build());
        }catch (Exception e){
            return ResponseDto.<CategoryDto>builder()
                    .message(DATABASE_ERROR + e.getMessage())
                    .success(true)
                    .build();
        }
    }

    @Override
    public ResponseDto<List<CategoryDto>> getAll() {
        try{
            return ResponseDto.<List<CategoryDto>>builder()
                    .message(OK)
                    .success(true)
                    .data(categoryRepository.findAll().stream().map(categoryMapper::toDto).toList())
                    .build();
        }catch (Exception e){
            return ResponseDto.<List<CategoryDto>>builder()
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<Void> delete(String id) {
        if (id == null){
            return ResponseDto.<Void>builder()
                    .message(NULL_ID)
                    .build();
        }
        try {
            Optional<Category> byId = categoryRepository.findById(id);
            if (byId.isEmpty()){
                return ResponseDto.<Void>builder()
                        .message(NOT_FOUND)
                        .build();
            }
            categoryRepository.deleteById(id);
            return ResponseDto.<Void>builder()
                    .message(OK)
                    .success(true)
                    .build();
        }catch (Exception e){
            return ResponseDto.<Void>builder()
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }
}
