package com.datn.beestyle.controller;

import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.dto.product.CreateProductRequest;
import com.datn.beestyle.service.product.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @GetMapping
    public ApiResponse<?> getProductsForAdmin(Pageable pageable) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Products", productService.getAll(pageable));
    }

    @PostMapping("/create")
    public ApiResponse<?> createProduct(@Valid @RequestBody CreateProductRequest request) {
        productService.create(request);
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Product added successfully");
    }

}
