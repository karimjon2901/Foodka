package com.example.foodka.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto{
    private String id;
    private String name;
    private String phoneNumber;
    private String password;
}
