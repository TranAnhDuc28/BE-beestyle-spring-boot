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

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
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
                                                        @RequestParam(required = false) String colorIds,
                                                        @RequestParam(required = false) String sizeIds,
                                                        @RequestParam(required = false) String status
    ) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Product variants",
                productVariantService.getProductVariantsByFieldsByProductId(pageable, productId, keyword, colorIds, sizeIds, status));
    }
    @GetMapping("/product/{productId}/filter/variant")
    public ApiResponse<?> getProductVariantsByProductId(Pageable pageable,
                                                        @PathVariable("productId") String productId,
                                                        @RequestParam(required = false) String colorIds,
                                                        @RequestParam(required = false) String sizeIds,
                                                        @RequestParam(required = false) BigDecimal minPrice,
                                                        @RequestParam(required = false) BigDecimal maxPrice
    ) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Product variants filter",
                productVariantService.filterProductVariantsByStatusIsActive(pageable, productId, colorIds, sizeIds, minPrice, maxPrice));
    }

    @GetMapping("/productVariant")
    public Optional<Object[]> getAllProductsWithDetails(@RequestParam List<Long> productIds) {
        return productVariantService.getAllProductsWithDetails(productIds);
    }

    @PutMapping("/productVariant/update/{id}")
    public ApiResponse<?> updateProducrVariant(@Min(1) @PathVariable long id,
                                               @Valid @RequestBody UpdateProductVariantRequest request) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Sửa chi tiết sản phẩm thành công!",
                productVariantService.update(id, request));
    }

    //    @PatchMapping("/updates")
//    public ApiResponse<?> updateProductVariant(@RequestBody List<@Valid UpdateProductVariantRequest> requestList) {
//        iProductVariantService.updateEntities(requestList);
//        return new ApiResponse<>(HttpStatus.CREATED.value(), "ProductVariant cập nhật thành công");
//    }

    @PutMapping("/productVariant/updates")
    public ResponseEntity<ApiResponse<String>> updateProductVariantCreate(@Valid @RequestBody UpdateProductVariantRequest request) {
        System.out.println(request);
        productVariantService.updateProductVariantCreate(request.getPromotionId(), request.getVariantIds());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK.value(), "Sửa chi tiết sản phẩm thành công!"));
    }
    //    @PutMapping("/productVariant/updatess")
//    public ResponseEntity<ApiResponse<String>> updateProductVariantUpdate(@Valid @RequestBody UpdateProductVariantRequest request) {
//        System.out.println(request);
//        productVariantService.updateProductVariantUpdate(request.getPromotionId(), request.getVariantIds());
//        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK.value(), "Sửa chi tiết sản phẩm thành công!"));
//    }
    @GetMapping("/products/{promotionId}")
    public ResponseEntity<Map<String, List<Long>>> getProductsByPromotionId(@PathVariable Long promotionId) {
        Map<String, List<Long>> productAndDetailIds = productVariantService.getProductAndDetailIdsByPromotionId(promotionId);
        return ResponseEntity.ok(productAndDetailIds);
    }
    @PutMapping("/{promotionId}/delete")
    public ResponseEntity<Void> removePromotionFromNonSelectedVariants(
            @PathVariable Integer promotionId,
            @RequestBody Integer ids) {

        try {
            productVariantService.removePromotionFromNonSelectedVariants(promotionId, ids);
            return ResponseEntity.ok().build(); // Trả về 200 OK nếu thành công
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Trả về 500 nếu có lỗi
        }
    }
}