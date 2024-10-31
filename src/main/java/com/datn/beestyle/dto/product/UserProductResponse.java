package com.datn.beestyle.dto.product;

import com.datn.beestyle.enums.GenderProduct;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProductResponse {
    Long id;
    String productName;
    String imageUrl;
    String genderProduct;
    Integer brandId;
    String brandName;
    Integer materialId;
    String materialName;
    String description;

    public UserProductResponse(Long id, String productName, String imageUrl, Integer genderProduct, Integer brandId,
                               String brandName, Integer materialId, String materialName, String description) {
        this.id = id;
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.genderProduct = GenderProduct.fromInteger(genderProduct);
        this.brandId = brandId;
        this.brandName = brandName;
        this.materialId = materialId;
        this.materialName = materialName;
        this.description = description;
    }
}
