package com.datn.beestyle.dto.order.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderItemRequest {
    Long orderId;
    Long productVariantId;
    Integer quantity;
    BigDecimal salePrice;
    BigDecimal discountedPrice;;
}
