package com.datn.beestyle.controller.user;

import com.datn.beestyle.dto.cart.CartCheckRequest;
import com.datn.beestyle.dto.product.variant.ProductVariantResponse;
import com.datn.beestyle.service.user.UserProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Validated
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class ShoppingCartCheckController {

    private final UserProductVariantService productVariantService;

    @PostMapping("/check")
    public ResponseEntity<List<ProductVariantResponse>> checkCart(
            @RequestBody List<CartCheckRequest> cartItemsRequest
    ) {
        List<ProductVariantResponse> productVariantResponses = this.productVariantService
                .getProductVariantByIds(cartItemsRequest);
        return ResponseEntity.ok(Objects.requireNonNullElseGet(productVariantResponses, ArrayList::new));
    }
}

