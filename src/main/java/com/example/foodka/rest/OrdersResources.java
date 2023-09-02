package com.example.foodka.rest;

import com.example.foodka.dto.OrdersDto;
import com.example.foodka.dto.ResponseDto;
import com.example.foodka.service.OrdersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrdersResources {
    private final OrdersService ordersService;

    @Operation(
            method = "Add new order",
            description = "Need to send OrderDto to this endpoint to create new order",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Order info",
                    content = @Content(mediaType = "application/json"))
    )
    @PostMapping
    public ResponseDto<OrdersDto> add(@RequestBody OrdersDto ordersDto){
        return ordersService.add(ordersDto);
    }

    @Operation(
            method = "Get all orders",
            description = "This endpoint return Page of orders. Need to send size and page to get orders",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Order info",
                    content = @Content(mediaType = "application/json"))
    )
    @GetMapping
    public ResponseDto<Page<OrdersDto>> getAll(@RequestParam(defaultValue = "10") Integer size,
                                               @RequestParam(defaultValue = "0") Integer page){
        return ordersService.getAll(size, page);
    }

    @Operation(
            method = "Get order by id",
            description = "Need to send id to this endpoint to get order",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Order info",
                    content = @Content(mediaType = "application/json"))
    )
    @GetMapping("/by-id/{id}")
    public ResponseDto<OrdersDto> getById(@PathVariable Integer id){
        return ordersService.getById(id);
    }

    @Operation(
            method = "Get order by user id",
            description = "Need to send user id to this endpoint to get order",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Order info",
                    content = @Content(mediaType = "application/json"))
    )
    @GetMapping("/by-user-id/{id}")
    public ResponseDto<List<OrdersDto>> getByUserId(@PathVariable Integer id){
        return ordersService.getByUserId(id);
    }

    @Operation(
            method = "Update order",
            description = "Need to send OrderDto to this endpoint to update order",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Order info",
                    content = @Content(mediaType = "application/json"))
    )
    @PatchMapping
    public ResponseDto<OrdersDto> update(@RequestBody OrdersDto ordersDto){
        return ordersService.update(ordersDto);
    }
}