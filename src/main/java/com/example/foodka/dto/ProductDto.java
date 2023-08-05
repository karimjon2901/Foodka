package com.example.foodka.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private CategoryDto category;
}
