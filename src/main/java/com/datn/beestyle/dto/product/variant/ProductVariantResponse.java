package com.datn.beestyle.dto.product.variant;

import com.datn.beestyle.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductVariantResponse {
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

    String brandName;
    String materialName;
    String imageUrl;
    String promotionName;

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
    @JsonPropertyOrder({ "productId", "id", "sku", "productName", "brandName", "materialName", "colorName", "sizeName", "originalPrice", "quantityInStock", "imageUrl", "promotionName" })
    public ProductVariantResponse(Long productId, String productName, String brandName, String materialName, Long id,
                                  String sku, String colorName, String sizeName, BigDecimal originalPrice,
                                  Integer quantityInStock, String imageUrl, String promotionName) {
        this.productId = productId;
        this.productName = productName;
        this.brandName = brandName;
        this.materialName = materialName;
        this.id = id;
        this.sku = sku;
        this.colorName = colorName;
        this.sizeName = sizeName;
        this.originalPrice = originalPrice;
        this.quantityInStock = quantityInStock;
        this.imageUrl = imageUrl;
        this.promotionName = promotionName;
    }

}
