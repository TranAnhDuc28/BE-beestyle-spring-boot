package com.datn.beestyle.dto.product.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProductResponse {
    Long id;
    String productCode;
    String productName;
    String imageUrl;
    BigDecimal originalPrice;
    BigDecimal salePrice;
    String sku;
    String categoryName;
    String brandName;
    String sizeName;
    String colorCode;
    String colorName;
    Integer quantity;
    String description;

    public UserProductResponse(Long id, String productName, String imageUrl, BigDecimal salePrice,
                               BigDecimal originalPrice) {
        this.id = id;
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.originalPrice = originalPrice;
        this.salePrice = salePrice;
    }

    public UserProductResponse(
            Long id, String productCode, String productName, BigDecimal originalPrice,
            BigDecimal salePrice, String sku, String categoryName, String brandName, Integer quantity,
            String colorCode, String colorName, String sizeName, String description
    ) {
        this.id = id;
        this.productCode = productCode;
        this.productName = productName;
        this.originalPrice = originalPrice;
        this.salePrice = salePrice;
        this.sku = sku;
        this.categoryName = categoryName;
        this.brandName = brandName;
        this.quantity = quantity;
        this.colorCode = colorCode;
        this.colorName = colorName;
        this.sizeName = sizeName;
        this.description = description;
    }
}
