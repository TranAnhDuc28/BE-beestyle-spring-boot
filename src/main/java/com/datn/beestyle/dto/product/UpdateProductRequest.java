package com.datn.beestyle.dto.product;

import com.datn.beestyle.dto.category.UserCategoryResponse;
import com.datn.beestyle.dto.product.attributes.brand.UserBrandResponse;
import com.datn.beestyle.dto.product.attributes.material.UserMaterialResponse;
import com.datn.beestyle.enums.Gender;
import com.datn.beestyle.validation.EnumValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateProductRequest {

    @NotBlank(message = "Không để trống trường")
    @Size(max = 255)
    String productName;

    String imageUrl;

    @EnumValue(enumClass = Gender.class, name = "Gender", message = "Invalid value for Gender")
    Gender gender;

    Integer brandId;
    Integer materialId;
    Integer categoryId;
    String description;
    String status;
}
