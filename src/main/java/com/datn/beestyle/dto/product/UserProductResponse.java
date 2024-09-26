package com.datn.beestyle.dto.product;

import com.datn.beestyle.dto.product.attributes.brand.UserBrandResponse;
import com.datn.beestyle.dto.product.attributes.material.UserMaterialResponse;
import com.datn.beestyle.enums.Gender;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProductResponse {
    Long id;
    String productName;
    String imageUrl;
    Gender gender;
    BigDecimal minPrice;
    BigDecimal maxPrice;
    UserBrandResponse brand;
    UserMaterialResponse material;
    String description;
}
