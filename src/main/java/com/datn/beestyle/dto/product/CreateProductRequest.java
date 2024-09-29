package com.datn.beestyle.dto.product;

import com.datn.beestyle.entity.product.ProductVariant;
import com.datn.beestyle.enums.Gender;
import com.datn.beestyle.validation.EnumValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateProductRequest {

    @NotBlank(message = "Không để trống trường")
    @Size(max = 255)
    String productName;

    String imageUrl;

    @EnumValue(name = "Gender", enumClass = Gender.class)
    String gender;

    Integer brandId;
    Integer materialId;
    Integer categoryId;
    String description;
    String status;
    List<ProductVariant> productVariants = new ArrayList<>();

}
