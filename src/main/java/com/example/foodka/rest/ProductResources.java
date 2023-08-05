package com.example.foodka.rest;

import com.example.foodka.dto.ProductDto;
import com.example.foodka.dto.ResponseDto;
import com.example.foodka.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductResources {
    private final ProductService productService;

    @Operation(
            method = "Add new product",
            description = "Need to send ProductDto to this endpoint to create new product",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product info",
                    content = @Content(mediaType = "application/json"))
    )
    @PostMapping
    public ResponseDto<ProductDto> addProduct(@RequestBody ProductDto productDto){
        return productService.addProduct(productDto);
    }

    @Operation(
            method = "Get all products",
            description = "This endpoint return Page of products. Need to send size and page to get products",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product info",
                    content = @Content(mediaType = "application/json"))
    )
    @GetMapping()
    public ResponseDto<Page<ProductDto>> getAllProducts(@RequestParam(defaultValue = "10") Integer size,
                                                        @RequestParam(defaultValue = "0") Integer page){
        return productService.getAll(size, page);
    }

    @Operation(
            method = "Get product by category id",
            description = "Need to send category id to this endpoint to get products",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product info",
                    content = @Content(mediaType = "application/json"))
    )
    @GetMapping("/by-category/{id}")
    public ResponseDto<List<ProductDto>> getByCategory(@PathVariable Integer id){
        return productService.getProductsByCategoryId(id);
    }

    @Operation(
            method = "Get product by id",
            description = "Need to send id to this endpoint to get product",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product info",
                    content = @Content(mediaType = "application/json"))
    )
    @GetMapping("/{id}")
    public ResponseDto<ProductDto> getProductById(@PathVariable Integer id){
        return productService.getProductById(id);
    }

    @Operation(
            method = "Update product",
            description = "Need to send ProductDto to this endpoint to update product",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product info",
                    content = @Content(mediaType = "application/json"))
    )
    @PatchMapping
    public ResponseDto<ProductDto> updateProduct(@RequestBody ProductDto productDto){
        return productService.updateProduct(productDto);
    }

    @Operation(
            method = "Delete product",
            description = "Need to send product id to this endpoint to delete this product",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product info",
                    content = @Content(mediaType = "application/json"))
    )
    @DeleteMapping("/{id}")
    public ResponseDto<Void> deleteProduct(@PathVariable Integer id){
        return productService.delete(id);
    }
}
