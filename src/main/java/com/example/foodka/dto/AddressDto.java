package com.example.foodka.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private String id;
    private String title;
    private String subTitle;
    private Double lat;
    private Double lon;
    private UsersDto user;
}
