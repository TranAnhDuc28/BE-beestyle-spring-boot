package com.datn.beestyle.controller.user;

import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.service.product.IProductService;
import com.datn.beestyle.service.user.product.UserProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Tag(name = "User Product Controller")
public class UserProductController {

    private final UserProductService productService;

    @GetMapping
    public ApiResponse<?> featuredProducts(
            @RequestParam(name = "q", required = false) Integer q
    ) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Products Area", productService.getFeaturedProductService(q));
    }

    @GetMapping("/seller")
    public ApiResponse<?> sellingProducts() {
        return new ApiResponse<>(HttpStatus.OK.value(), "Products Seller",
                productService.getSellerProductService()
        );
    }

    @GetMapping("/offer")
    public ApiResponse<?> offeringProducts() {
        return new ApiResponse<>(HttpStatus.OK.value(), "Products Offer",
                productService.getOfferProductService()
        );
    }

    @GetMapping("/{productId}/variant/image")
    public ApiResponse<?> getImageSingleProduct(@PathVariable Long productId) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Products Image Product Variant",
                productService.getImageProducVariant(productId)
        );
    }

    @GetMapping("/{productId}/variant/color")
    public ApiResponse<?> getColorSingleProduct(@PathVariable Long productId) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Products Colors Product Variant",
                productService.getAllColorLists(productId)
        );
    }

    @GetMapping("/{productId}/variant/size")
    public ApiResponse<?> getSizeSingleProduct(
            @PathVariable Long productId,
            @RequestParam(name = "c") String colorCode
    ) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Products Sizes Product Variant",
                productService.getAllSizeByPdAndColor(productId, colorCode)
        );
    }

    @GetMapping("/{productId}/variant")
    public ApiResponse<?> getProductVariant(
            @PathVariable Long productId,
            @RequestParam(name = "c", required = false) String colorCode,
            @RequestParam(name = "s", required = false) Long sizeId
    ) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Products Variant",
                productService.getProductVariantUser(productId, colorCode, sizeId)
        );
    }
}
