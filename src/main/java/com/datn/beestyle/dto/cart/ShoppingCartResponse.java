package com.datn.beestyle.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ShoppingCartResponse {
    Long id;
    Long productVariantId;
    Long customerId;
    String cartCode;
    Integer quantity;
    BigDecimal salePrice;
    BigDecimal discountedPrice;

    public ShoppingCartResponse(
            Long id, Long productVariantId, Long customerId,
            String cartCode, Integer quantity,
            BigDecimal salePrice, BigDecimal discountedPrice
    ) {
        this.id = id;
        this.productVariantId = productVariantId;
        this.customerId = customerId;
        this.cartCode = cartCode;
        this.quantity = quantity;
        this.salePrice = salePrice;
        this.discountedPrice = discountedPrice;
    }
}
