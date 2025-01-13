package com.datn.beestyle.dto.cart;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShoppingCartRequest {
    @NotNull
    private Long productVariantId;
    private Long customerId;

    @NotBlank
    private String cartCode;

    @NotNull
    @Min(value = 1)
    @Max(value = Integer.MAX_VALUE)
    private Integer quantity;

    @NotNull
    @Min(value = 0)
    BigDecimal salePrice;

    @NotNull
    @Min(value = 0)
    BigDecimal discountedPrice;
}
