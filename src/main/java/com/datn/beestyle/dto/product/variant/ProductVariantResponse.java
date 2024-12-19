package com.datn.beestyle.dto.product.variant;

import com.datn.beestyle.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductVariantResponse {
    Long id;
    String sku;
    Long productId;
    String productCode;
    String productName;
    String categoryName;
    String brandName;
    Integer colorId;
    String colorCode;
    String colorName;
    Integer sizeId;
    String sizeName;
    BigDecimal salePrice;
    Integer quantityInStock;
    BigDecimal originalPrice;
    String description;
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

    public ProductVariantResponse(
            Long id, String productCode, String productName, BigDecimal salePrice,
            String sku, String categoryName, String brandName, Integer quantity,
            String colorCode, String colorName, String sizeName, String description
    ) {
        this.id = id;
        this.productCode = productCode;
        this.productName = productName;
        this.salePrice = salePrice;
        this.sku = sku;
        this.categoryName = categoryName;
        this.brandName = brandName;
        this.quantityInStock = quantity;
        this.colorCode = colorCode;
        this.colorName = colorName;
        this.sizeName = sizeName;
        this.description = description;
    }
}
