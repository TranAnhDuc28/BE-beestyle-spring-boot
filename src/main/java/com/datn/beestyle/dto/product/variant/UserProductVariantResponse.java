package com.datn.beestyle.dto.product.variant;

import com.datn.beestyle.dto.product.attributes.color.UserColorResponse;
import com.datn.beestyle.dto.size.UserSizeResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProductVariantResponse {
    Long id;
    String sku;
    Long productId;
    String productName;
    Integer colorId;
    String colorName;
    Integer sizeId;
    String sizeName;
    BigDecimal salePrice;
    Integer quantityInStock;

    public UserProductVariantResponse(Long id, String sku, Long productId, String productName,
                                      Integer colorId, String colorName, Integer sizeId, String sizeName,
                                      BigDecimal salePrice, Integer quantityInStock) {
        this.id = id;
        this.sku = sku;
        this.productId = productId;
        this.productName = productName;
        this.colorId = colorId;
        this.colorName = colorName;
        this.sizeId = sizeId;
        this.sizeName = sizeName;
        this.salePrice = salePrice;
        this.quantityInStock = quantityInStock;
    }
}
