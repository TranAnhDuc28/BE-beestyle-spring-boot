package com.datn.beestyle.dto.order.item;

<<<<<<< HEAD
import com.datn.beestyle.entity.product.ProductVariant;
=======
import com.fasterxml.jackson.annotation.JsonInclude;
>>>>>>> ee23191801e6c6287e495bb989978a11a4ae2e84
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
<<<<<<< HEAD
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
=======
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemResponse {
    Long id;
    Long orderId;
    Long productVariantId;
    String sku;
    Long productId;
    String productName;
    Integer colorId;
    String colorCode;
    String colorName;
    Integer sizeId;
    String sizeName;
    Integer quantity;
    BigDecimal salePrice;
    BigDecimal discountedPrice;
    String note;
>>>>>>> ee23191801e6c6287e495bb989978a11a4ae2e84
}
