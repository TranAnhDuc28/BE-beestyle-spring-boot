package com.datn.beestyle.dto.product.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProductResponse {
    Long id;
    String productName;
    String imageUrl;
    BigDecimal originalPrice;
    BigDecimal salePrice;
    public UserProductResponse(Long id, String productName, String imageUrl, BigDecimal salePrice,
                           BigDecimal originalPrice) {
        this.id = id;
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.originalPrice = originalPrice;
        this.salePrice = salePrice;
    }
}
