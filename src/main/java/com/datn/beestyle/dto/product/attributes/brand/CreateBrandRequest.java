package com.datn.beestyle.dto.product.attributes.brand;

import com.datn.beestyle.enums.Status;
import com.datn.beestyle.validation.EnumValue;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateBrandRequest {

    @NotBlank(message = "Không để trống trường")
    String brandName;
}
