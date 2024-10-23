package com.datn.beestyle.controller;

import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.dto.product.CreateProductRequest;
import com.datn.beestyle.service.product.IProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Product Controller")
public class ProductController {

    private final IProductService productService;

    @GetMapping
    public ApiResponse<?> getProducts(Pageable pageable,
                                      @RequestParam(required = false) String keyword,
                                      @RequestParam(required = false) String category,
                                      @RequestParam(required = false) String gender,
                                      @RequestParam(required = false) String brand,
                                      @RequestParam(required = false) String material,
                                      @RequestParam(required = false) String status
                                      ) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Products",
                productService.getProductsByFields(pageable, keyword, category, gender, brand, material, status));
    }

    @PostMapping("/create")
    public ApiResponse<?> createProduct(@Valid @RequestBody CreateProductRequest request) {
        productService.create(request);
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Product added successfully");
    }

}
