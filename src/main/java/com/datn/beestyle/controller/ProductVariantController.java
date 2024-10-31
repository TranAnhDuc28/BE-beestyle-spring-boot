package com.datn.beestyle.controller;

import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.service.product.variant.ProductVariantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "Product Variant Controller")
public class ProductVariantController {

    private final ProductVariantService productVariantService;

    @GetMapping("/product/{productId}/variant")
    public ApiResponse<?> getProductVariantsByProductId(Pageable pageable,
                                                        @PathVariable("productId") String productId,
                                                        @RequestParam(required = false) String keyword,
                                                        @RequestParam(required = false) String color,
                                                        @RequestParam(required = false) String size,
                                                        @RequestParam(name = "status",required = false) String status
    ) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Product variants",
                productVariantService.getProductsByFieldsByProductId(pageable, productId, keyword, color, size, status));
    }
}   

