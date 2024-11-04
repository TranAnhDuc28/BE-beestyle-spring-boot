package com.datn.beestyle.dto.product;

import com.datn.beestyle.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse extends UserProductResponse {
    Integer categoryId;
    String categoryName;
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Long createdBy;
    Long updatedBy;

    public ProductResponse(Long id, String productCode, String productName, String imageUrl, Integer genderProduct,
                           Integer brandId, String brandName, Integer materialId, String materialName, String description,
                           Integer categoryId, String categoryName, Integer status, LocalDateTime createdAt,
                           LocalDateTime updatedAt, Long createdBy, Long updatedBy) {
        super(id, productCode, productName, imageUrl, genderProduct, brandId, brandName, materialId, materialName, description);
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.status = Status.fromInteger(status);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }
}
