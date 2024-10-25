package com.datn.beestyle.dto.product;

import com.datn.beestyle.dto.product.attributes.brand.UserBrandResponse;
import com.datn.beestyle.dto.product.attributes.material.UserMaterialResponse;
import com.datn.beestyle.enums.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProductResponse {
    Long id;
    String productName;
    String imageUrl;
    String gender;
    Integer brandId;
    String brandName;
    Integer materialId;
    String materialName;
    String description;

    public UserProductResponse(Long id, String productName, String imageUrl, Integer gender, Integer brandId,
                               String brandName, Integer materialId, String materialName, String description) {
        this.id = id;
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.gender = Gender.fromInteger(gender);
        this.brandId = brandId;
        this.brandName = brandName;
        this.materialId = materialId;
        this.materialName = materialName;
        this.description = description;
    }
}
