package com.example.foodka.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDto {
    private String id;
    private UsersDto user;
    private Double price;
    private Integer status = 0;
    private String description;
    private List<ProductDto> products;
    private AddressDto address;
    private Integer time;
    private LocalDateTime createdAt;
}
