package com.datn.beestyle.dto.product.variant;

import com.datn.beestyle.dto.product.attributes.brand.BrandResponse;
import com.datn.beestyle.dto.product.attributes.material.MaterialResponse;
import com.datn.beestyle.enums.Gender;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    Long id;

    String productName;

    String imageUrl;

    Gender gender;

    BrandResponse brandResponse;

    MaterialResponse materialResponse;

    String description;

    Boolean deleted;
}
