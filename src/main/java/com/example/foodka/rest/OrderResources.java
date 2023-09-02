package com.example.foodka.rest;

import com.example.foodka.dto.OrderDto;
import com.example.foodka.dto.ResponseDto;
import com.example.foodka.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderResources {
    private final OrderService orderService;

    @Operation(
            method = "Add new order",
            description = "Need to send OrderDto to this endpoint to create new order",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Order info",
                    content = @Content(mediaType = "application/json"))
    )
    @PostMapping
    public ResponseDto<OrderDto> add(@RequestBody OrderDto orderDto){
        return orderService.add(orderDto);
    }

    @Operation(
            method = "Get all orders",
            description = "This endpoint return Page of orders. Need to send size and page to get orders",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Order info",
                    content = @Content(mediaType = "application/json"))
    )
    @GetMapping
    public ResponseDto<Page<OrderDto>> getAll(@RequestParam(defaultValue = "10") Integer size,
                                              @RequestParam(defaultValue = "0") Integer page){
        return orderService.getAll(size, page);
    }

    @Operation(
            method = "Get order by id",
            description = "Need to send id to this endpoint to get order",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Order info",
                    content = @Content(mediaType = "application/json"))
    )
    @GetMapping("/by-id/{id}")
    public ResponseDto<OrderDto> getById(@PathVariable Integer id){
        return orderService.getById(id);
    }

    @Operation(
            method = "Get order by user id",
            description = "Need to send user id to this endpoint to get order",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Order info",
                    content = @Content(mediaType = "application/json"))
    )
    @GetMapping("/by-user-id/{id}")
    public ResponseDto<List<OrderDto>> getByUserId(@PathVariable Integer id){
        return orderService.getByUserId(id);
    }

    @Operation(
            method = "Update order",
            description = "Need to send OrderDto to this endpoint to update order",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Order info",
                    content = @Content(mediaType = "application/json"))
    )
    @PatchMapping
    public ResponseDto<OrderDto> update(@RequestBody OrderDto orderDto){
        return orderService.update(orderDto);
    }
}