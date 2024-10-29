package com.datn.beestyle.dto.product.variant;

import com.datn.beestyle.dto.product.attributes.color.UserColorResponse;
import com.datn.beestyle.dto.size.UserSizeResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProductVariantResponse {
    Long id;
    String sku;
    String productName;
    Integer colorId;
    String colorName;
    Integer sizeId;
    Integer sizeName;
    BigDecimal salePrice;
    Integer quantityInStock;
}
