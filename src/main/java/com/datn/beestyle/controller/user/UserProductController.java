package com.datn.beestyle.controller.user;

import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/user/product")
@RequiredArgsConstructor
public class UserProductController {

    private final IProductService productService;

    @GetMapping
    public ApiResponse<?> getProductsForUser(Pageable pageable) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Products", productService.getAll(pageable));
    }
}
