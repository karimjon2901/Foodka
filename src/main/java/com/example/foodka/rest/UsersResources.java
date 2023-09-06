package com.example.foodka.rest;

import com.example.foodka.dto.LoginDto;
import com.example.foodka.dto.ResponseDto;
import com.example.foodka.dto.UsersDto;
import com.example.foodka.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsersResources {
    private final UsersService usersService;

    @Operation(
            method = "Add new user",
            description = "Need to send UsersDto to this endpoint to create new user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Users info",
                    content = @Content(mediaType = "application/json"))
    )
    @PostMapping
    public ResponseDto<String> add(@RequestBody UsersDto usersDto){
        return usersService.add(usersDto);
    }

    @Operation(
            method = "Update user",
            description = "Need to send UsersDto to this endpoint to update user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Users info",
                    content = @Content(mediaType = "application/json"))
    )
    @PatchMapping
    public ResponseDto<UsersDto> updateUser(@RequestBody UsersDto usersDto){
        return usersService.update(usersDto);
    }

    @Operation(
            method = "Login user",
            description = "Need to send username and password to this endpoint to login user. You can get token.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Users info",
                    content = @Content(mediaType = "application/json"))
    )
    @PostMapping("/login")
    public ResponseDto<String> loginUser(@RequestBody LoginDto loginDto) throws NoSuchMethodException {
        return usersService.loginUser(loginDto);
    }
    @Operation(
            method = "Get user by phone number",
            description = "Need to send phone number to this endpoint to get user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Users info",
                    content = @Content(mediaType = "application/json"))
    )
    @GetMapping("/by-phone-number")
    public ResponseDto<UsersDto> getUserByPhoneNumber(@RequestParam String phoneNumber){
        return usersService.getUserByPhoneNumber(phoneNumber);
    }

    @Operation(
            method = "Get user by id",
            description = "Need to send id to this endpoint to get user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Users info",
                    content = @Content(mediaType = "application/json"))
    )
    @GetMapping("/{id}")
    public ResponseDto<UsersDto> getById(@PathVariable Integer id){
        return usersService.getById(id);
    }

    @Operation(
            method = "Get all user",
            description = "This endpoint return all users",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Users info",
                    content = @Content(mediaType = "application/json"))
    )
    @GetMapping
    public ResponseDto<List<UsersDto>> getAll(){
        return usersService.getAll();
    }
}
