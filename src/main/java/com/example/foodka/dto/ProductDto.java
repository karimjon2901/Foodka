package com.example.foodka.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String id;
    private TranslatorDto name;
    private TranslatorDto description;
    private Double price;
    private CategoryDto category;
}
