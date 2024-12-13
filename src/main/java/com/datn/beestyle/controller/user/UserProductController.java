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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Tag(name = "User Product Controller")
public class UserProductController {

    private final UserProductService productService;

    @GetMapping
    public ApiResponse<?> getProductsForUser(
            @RequestParam(name = "q", required = false) Integer q
    ) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Products Area", productService.getFeaturedProducts(q));
    }

    @GetMapping("/search")
    public ApiResponse<?> searchProductsUser(
    ) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Products Search", productService.findProductUser());
    }

    @GetMapping("/seller")
    public ApiResponse<?> sellingProducts() {
        return new ApiResponse<>(HttpStatus.OK.value(), "Products Seller", productService.getSellerProducts());
    }

    @GetMapping("/offer")
    public ApiResponse<?> offeringProducts() {
        return new ApiResponse<>(HttpStatus.OK.value(), "Products Offer", productService.getOfferProductUser());
    }
}
