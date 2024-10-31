package com.datn.beestyle.dto.order.item;

import com.datn.beestyle.dto.product.UserProductResponse;
import com.datn.beestyle.dto.product.variant.ProductVariantResponse;
import com.datn.beestyle.entity.order.Order;
import com.datn.beestyle.entity.product.ProductVariant;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    UserProductResponse productResponse;
}
