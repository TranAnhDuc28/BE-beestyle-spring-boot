package com.datn.beestyle.dto.order.item;

import com.datn.beestyle.entity.product.ProductVariant;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemResponse {
    Long id;
    int quantity;
    BigDecimal originalPrice;
    BigDecimal discountedPrice;
    String note;
    ProductVariant productVariant;
}
