package com.datn.beestyle.dto.product.variant;

import com.datn.beestyle.enums.Status;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariantResponse extends UserProductVariantResponse {
    BigDecimal originalPrice;
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Long createdBy;
    Long updatedBy;

        public ProductVariantResponse(Long id, String sku, Long productId, String productName, Integer colorId,
                                      String colorName, Integer sizeId, String sizeName, BigDecimal salePrice,
                                      Integer quantityInStock, BigDecimal originalPrice, Integer status,
                                      LocalDateTime createdAt, LocalDateTime updatedAt, Long createdBy, Long updatedBy) {
            super(id, sku, productId, productName, colorId, colorName, sizeId, sizeName, salePrice, quantityInStock);
            this.originalPrice = originalPrice;
            this.status = Status.fromInteger(status);
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.createdBy = createdBy;
            this.updatedBy = updatedBy;
        }
}
