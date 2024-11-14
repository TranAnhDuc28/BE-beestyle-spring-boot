package com.datn.beestyle.dto.product.variant;

import com.datn.beestyle.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariantResponse{
    Long id;
    String sku;
    Long productId;
    String productName;
    Integer colorId;
    String colorCode;
    String colorName;
    Integer sizeId;
    String sizeName;
    BigDecimal salePrice;
    Integer quantityInStock;
    BigDecimal originalPrice;
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Long createdBy;
    Long updatedBy;

    public ProductVariantResponse(Long id, String sku, Long productId, String productName, Integer colorId,
                                  String colorCode, String colorName, Integer sizeId, String sizeName, BigDecimal salePrice,
                                  Integer quantityInStock, BigDecimal originalPrice, Integer status, LocalDateTime createdAt,
                                  LocalDateTime updatedAt, Long createdBy, Long updatedBy) {
        this.id = id;
        this.sku = sku;
        this.productId = productId;
        this.productName = productName;
        this.colorId = colorId;
        this.colorCode = colorCode;
        this.colorName = colorName;
        this.sizeId = sizeId;
        this.sizeName = sizeName;
        this.salePrice = salePrice;
        this.quantityInStock = quantityInStock;
        this.originalPrice = originalPrice;
        this.status = Status.fromInteger(status);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public ProductVariantResponse(Long id, String sku, Long productId, String productName, Integer colorId,
                                  String colorCode, String colorName, Integer sizeId, String sizeName,
                                  BigDecimal salePrice, Integer quantityInStock) {
        this.id = id;
        this.sku = sku;
        this.productId = productId;
        this.productName = productName;
        this.colorId = colorId;
        this.colorCode = colorCode;
        this.colorName = colorName;
        this.sizeId = sizeId;
        this.sizeName = sizeName;
        this.salePrice = salePrice;
        this.quantityInStock = quantityInStock;
    }
}
