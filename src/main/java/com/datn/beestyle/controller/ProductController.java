package com.datn.beestyle.controller;

import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.dto.material.UpdateMaterialRequest;
import com.datn.beestyle.dto.product.CreateProductRequest;
import com.datn.beestyle.dto.product.UpdateProductRequest;
import com.datn.beestyle.dto.product.variant.ProductVariantResponse;
import com.datn.beestyle.dto.product.variant.UpdateProductVariantRequest;
import com.datn.beestyle.dto.promotion.UpdatePromotionRequest;
import com.datn.beestyle.entity.product.Product;
import com.datn.beestyle.service.product.IProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Thêm sản phẩm thành công.",
                productService.create(request));
    }

    @PutMapping("/update/{id}")
    public ApiResponse<?> updateProduct(@Min(1) @PathVariable long id,
                                        @Valid @RequestBody UpdateProductRequest request) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Cập nhật sản phẩm thành công.",
                productService.update(id, request));
    }

}
