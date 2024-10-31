package com.datn.beestyle.controller;

import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.dto.product.variant.UpdateProductVariantRequest;
import com.datn.beestyle.service.product.variant.ProductVariantService;
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
    @GetMapping("/productVariant")
    public Optional<Object[]> getAllProductsWithDetails(@RequestParam List<Long> productIds) {
        return productVariantService.getAllProductsWithDetails(productIds);
    }
    @PutMapping("/productVariant/update/{id}")
    public ApiResponse<?> updateProducrVariant(@Min(1) @PathVariable long id,
                                               @Valid @RequestBody UpdateProductVariantRequest request) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Sửa chi tiets sp thành công!",
                productVariantService.update(id, request));
    }
    //    @PatchMapping("/updates")
//    public ApiResponse<?> updateProductVariant(@RequestBody List<@Valid UpdateProductVariantRequest> requestList) {
//        iProductVariantService.updateEntities(requestList);
//        return new ApiResponse<>(HttpStatus.CREATED.value(), "ProductVariant cập nhật thành công");
//    }
    @PutMapping("/productVariant/updates")
    public ResponseEntity<ApiResponse<String>> updateProductVariant(@Valid @RequestBody UpdateProductVariantRequest request) {
        System.out.println(request);
        productVariantService.updateProductVariant(request.getPromotionId(), request.getVariantIds());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK.value(), "Sửa chi tiết sản phẩm thành công!", null));
    }
}

