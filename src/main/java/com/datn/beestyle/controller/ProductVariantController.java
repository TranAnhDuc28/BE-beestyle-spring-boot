package com.datn.beestyle.controller;


import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.dto.product.variant.UpdateProductVariantRequest;
import com.datn.beestyle.dto.promotion.UpdatePromotionRequest;
import com.datn.beestyle.service.product.IProductService;
import com.datn.beestyle.service.product.variant.IProductVariantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/admin/productVariant")
@RequiredArgsConstructor
@Tag(name = "ProductVariant Controller")
public class ProductVariantController {

    private final IProductVariantService iProductVariantService;
    @GetMapping
    public Optional<Object[]> getAllProductsWithDetails(@RequestParam List<Long> productIds) {
        return iProductVariantService.getAllProductsWithDetails(productIds);
    }
    @PutMapping("/update/{id}")
    public ApiResponse<?> updateProducrVariant(@Min(1) @PathVariable long id,
                                          @Valid @RequestBody UpdateProductVariantRequest request) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Sửa chi tiets sp thành công!",
                iProductVariantService.update(id, request));
    }
//    @PatchMapping("/updates")
//    public ApiResponse<?> updateProductVariant(@RequestBody List<@Valid UpdateProductVariantRequest> requestList) {
//        iProductVariantService.updateEntities(requestList);
//        return new ApiResponse<>(HttpStatus.CREATED.value(), "ProductVariant cập nhật thành công");
//    }
    @PutMapping("/updates")
    public ResponseEntity<ApiResponse<String>> updateProductVariant(@Valid @RequestBody UpdateProductVariantRequest request) {
        System.out.println(request);
        iProductVariantService.updateProductVariant(request.getPromotionId(), request.getVariantIds());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK.value(), "Sửa chi tiết sản phẩm thành công!", null));
    }

}
