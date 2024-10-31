package com.datn.beestyle.dto.product;

import com.datn.beestyle.entity.product.ProductImage;
import com.datn.beestyle.entity.product.ProductVariant;
import com.datn.beestyle.enums.Gender;
import com.datn.beestyle.enums.GenderProduct;
import com.datn.beestyle.enums.Status;
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

    @EnumValue(enumClass = GenderProduct.class, name = "GenderProduct", message = "Invalid value for Gender")
    String genderProduct;

    Integer brandId;
    Integer materialId;
    Integer categoryId;
    String description;

    @EnumValue(enumClass = Status.class, name = "Status", message = "Invalid value for Status")
    String status;

    List<ProductImage> productImages = new ArrayList<>();

    List<ProductVariant> productVariants = new ArrayList<>();

}
