package com.datn.beestyle.controller.user;

import com.datn.beestyle.dto.cart.ShoppingCartRequest;
import com.datn.beestyle.dto.product.variant.ProductVariantResponse;
import com.datn.beestyle.service.shoppingcart.IShoppingCartService;
import com.datn.beestyle.service.user.UserProductVariantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Validated
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final UserProductVariantService productVariantService;
    private final IShoppingCartService shoppingCartService;

    @PostMapping("/check")
    public ResponseEntity<List<ProductVariantResponse>> checkCart(
            @RequestBody List<ShoppingCartRequest> cartItemsRequest
    ) {
        List<ProductVariantResponse> productVariantResponses = this.productVariantService
                .getProductVariantByIds(cartItemsRequest);
        return ResponseEntity.ok(Objects.requireNonNullElseGet(productVariantResponses, ArrayList::new));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateCartItems(
            @RequestBody @Valid List<ShoppingCartRequest> cartRequests
    ) {
        this.shoppingCartService.createCartItemOnline(cartRequests);
        return ResponseEntity.ok().body("Cart item update success");
    }
}

