package com.example.foodka.dto;

import com.example.foodka.appStatus.AppStatusMessages;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersInputDto {
    private String id;
    private UsersDto user;
    private String description;
    private AddressDto address;
    @NotNull(message = AppStatusMessages.NULL_VALUE)
    private List<ProductOrderDto> products;
}